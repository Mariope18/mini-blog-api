package com.example.blog.service;

import com.example.blog.dto.PostRequestDto;
import com.example.blog.dto.PostResponseDto;
import com.example.blog.model.Post;
import com.example.blog.model.User;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<PostResponseDto> findAll();

    List<PostResponseDto> findAllPostByUser(User user);

    Optional<PostResponseDto> findById(Long id);

    Optional<PostResponseDto> findByIdAndUser(Long id, User user);

    PostResponseDto createPost(PostRequestDto requestDto, User user);

    Optional<PostResponseDto> updatePost(Long id, PostRequestDto postRequestNew, User user);

    boolean deletePost(Long id, User user);
}
