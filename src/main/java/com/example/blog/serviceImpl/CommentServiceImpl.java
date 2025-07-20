package com.example.blog.serviceImpl;

import com.example.blog.dto.CommentRequestDto;
import com.example.blog.dto.CommentResponseDto;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.mapper.CommentMapper;
import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public List<CommentResponseDto> findAllByPostId(Long idPost) {
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new ResourceNotFoundException("Post non trovato con id: " + idPost));

        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public Optional<CommentResponseDto> findById(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto);
    }

    @Override
    @Transactional
    public CommentResponseDto createCommentByPost(Long idPost, CommentRequestDto commentRequest, User user) {
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new ResourceNotFoundException("Post non trovato con id: " + idPost));

        Comment comment = new Comment();
        comment.setText(commentRequest.text());
        comment.setPost(post);
        comment.setCreationDate(LocalDate.now());
        comment.setUser(user);

        Comment commentSaved = commentRepository.save(comment);

        return commentMapper.toDto(commentSaved);
    }

    @Override
    @Transactional
    public Optional<CommentResponseDto> updateComment(Long id, CommentRequestDto commentNew, User user) {
        return commentRepository.findByIdAndUser(id,user)
                .map(comment -> {
                    comment.setText(commentNew.text());
                    Comment commentSaved =  commentRepository.save(comment);
                    return commentMapper.toDto(commentSaved);
                });
    }

    @Override
    @Transactional
    public Boolean deleteComment(Long id, User user) {
        Optional<Comment> comment = commentRepository.findByIdAndUser(id,user);
        if(comment.isPresent()){
            commentRepository.delete(comment.get());
            return true;
        }
        return false;
    }
}
