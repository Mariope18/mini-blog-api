package com.example.blog.service;

import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.model.User;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> findAllByPostId(Long postId);

    Optional<Comment> findById(Long id);

    Comment createCommentByPost(Long idPost, Comment comment, User user);

    Optional<Comment> updateComment(Long id, Comment commentNew, User user);

    Boolean deleteComment(Long id, User user);
}
