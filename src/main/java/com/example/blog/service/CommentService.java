package com.example.blog.service;

import com.example.blog.dto.CommentRequestDto;
import com.example.blog.dto.CommentResponseDto;
import com.example.blog.model.Comment;
import com.example.blog.model.User;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<CommentResponseDto> findAllByPostId(Long postId);

    Optional<CommentResponseDto> findById(Long id);

    CommentResponseDto createCommentByPost(Long idPost, CommentRequestDto comment, User user);

    Optional<CommentResponseDto> updateComment(Long id, CommentRequestDto commentNew, User user);

    Boolean deleteComment(Long id, User user);
}
