package com.example.blog.repository;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByUser(User user);

    Optional<Post> findByIdAndUser(Long id, User user);
}
