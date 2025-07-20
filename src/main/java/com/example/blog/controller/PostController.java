package com.example.blog.controller;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    @Autowired // Puoi anche omettere @Autowired qui, Spring lo capisce da solo
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> findAll(){
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/user")
    public ResponseEntity<List<Post>> findAllByUser(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(postService.findAllPostByUser(user));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Post> findByIdAndUser(@PathVariable Long id, @AuthenticationPrincipal User user){
        return postService.findByIdAndUser(id,user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findById(@PathVariable Long id){
        return postService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/user")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @AuthenticationPrincipal User user){
        return new ResponseEntity<>(postService.createPost(post,user), HttpStatus.CREATED);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id,
                                           @RequestBody Post postNew,
                                           @AuthenticationPrincipal User user){

        return postService.updatePost(id,postNew,user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @AuthenticationPrincipal User user){
        boolean isDelete = postService.deletePost(id,user);
        if(isDelete){
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
