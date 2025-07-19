package com.example.blog.service;

import com.example.blog.model.Post;
import com.example.blog.model.User;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> findAll();

    List<Post> findAllPostByUser(User user);

    Optional<Post> findById(Long id);

    Optional<Post> findByIdAndUser(Long id, User user);

    Post createPost(Post post, User user);

    Optional<Post> updatePost(Long id, Post postNew, User user);

    boolean deletePost(Long id, User user);
}
