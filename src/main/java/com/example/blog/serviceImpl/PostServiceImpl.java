package com.example.blog.serviceImpl;

import com.example.blog.dto.PostRequestDto;
import com.example.blog.dto.PostResponseDto;
import com.example.blog.mapper.PostMapper;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private final PostMapper postMapper; // Inietta il mapper

    @Autowired
    public PostServiceImpl(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    @Override
    public List<PostResponseDto> findAll() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::toDto) // Usa il mapper per convertire ogni post
                .toList();
    }

    @Override
    public List<PostResponseDto> findAllPostByUser(User user) {
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    @Override
    public Optional<PostResponseDto> findById(Long id){
        return postRepository.findById(id)
                .map(postMapper::toDto);
    }

    @Override
    public Optional<PostResponseDto> findByIdAndUser(Long id, User user) {
        return postRepository.findByIdAndUser(id,user)
                .map(postMapper::toDto);
    }

    @Override
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        // 1. Converti il DTO in Entità
        Post post = new Post();
        post.setTitle(requestDto.title());
        post.setContent(requestDto.content());

        // 2. Imposta i campi gestiti dal server
        post.setUser(user);
        post.setCreationDate(LocalDate.now());

        // 3. Salva l'entità
        Post savedPost = postRepository.save(post);

        // 4. Riconverti l'entità salvata in un DTO di risposta
        return postMapper.toDto(savedPost);
    }

    @Override
    @Transactional
    public Optional<PostResponseDto> updatePost(Long id, PostRequestDto postRequestNew, User user) {
        Optional<Post> optionalPost = postRepository.findByIdAndUser(id,user);

        return optionalPost.map(post -> {
            if (postRequestNew.content() != null){
                post.setContent(postRequestNew.content());
            }
            if (postRequestNew.title() != null){
                post.setTitle(postRequestNew.title());
            }

            Post postNew = postRepository.save(post);
            return postMapper.toDto(postNew);
        });
    }

    @Override
    @Transactional
    public boolean deletePost(Long id, User user) {
        Optional<Post> optionalPost = postRepository.findByIdAndUser(id,user);

        if(optionalPost.isPresent()){
            postRepository.delete(optionalPost.get()); // <-- AGGIUNGI QUESTA RIGA
            return true;
        }

        return false;
    }
}
