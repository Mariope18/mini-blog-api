package com.example.blog.serviceImpl;

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

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public List<Comment> findAllByPostId(Long idPost) {
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new EntityNotFoundException("Post non trovato con id: " + idPost));
        return commentRepository.findByPost(post);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public Comment createCommentByPost(Long idPost, Comment comment, User user) {
        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new EntityNotFoundException("Post non trovato con id: " + idPost));

        comment.setPost(post);
        comment.setUser(user);
        comment.setCreationDate(LocalDate.now());

        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Optional<Comment> updateComment(Long id, Comment commentNew, User user) {
        return commentRepository.findByIdAndUser(id,user)
                .map(comment -> {
                    comment.setText(commentNew.getText());
                    return commentRepository.save(comment);
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
