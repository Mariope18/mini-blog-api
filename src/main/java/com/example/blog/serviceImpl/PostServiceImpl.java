package com.example.blog.serviceImpl;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findAllPostByUser(User user) {
        return postRepository.findByUser(user);
    }

    @Override
    public Optional<Post> findById(Long id){
        return postRepository.findById(id);
    }

    @Override
    public Optional<Post> findByIdAndUser(Long id, User user) {
        return postRepository.findByIdAndUser(id,user);
    }

    @Override
    @Transactional
    public Post createPost(Post post, User user) {
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Optional<Post> updatePost(Long id, Post postNew, User user) {
        Optional<Post> optionalPost = postRepository.findByIdAndUser(id,user);

        return optionalPost.map(post -> {
            if (postNew.getContent() != null){
                post.setContent(postNew.getContent());
            }
            if (postNew.getTitle() != null){
                post.setTitle(postNew.getTitle());
            }
            return postRepository.save(post);
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
