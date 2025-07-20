package com.example.blog.controller;

import com.example.blog.dto.CommentRequestDto;
import com.example.blog.dto.CommentResponseDto;
import com.example.blog.dto.PostRequestDto;
import com.example.blog.dto.PostResponseDto;
import com.example.blog.model.Comment;
import com.example.blog.model.User;
import com.example.blog.service.CommentService;
import com.example.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;
    private CommentService commentService;

    @Autowired // Puoi anche omettere @Autowired qui, Spring lo capisce da solo
    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> findAll(){
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/user")
    public ResponseEntity<List<PostResponseDto>> findAllByUser(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(postService.findAllPostByUser(user));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<PostResponseDto> findByIdAndUser(@PathVariable Long id, @AuthenticationPrincipal User user){
        return postService.findByIdAndUser(id,user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long id){
        return postService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/user")
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto requestDto, // Accetta un DTO e lo valida
                                                      @AuthenticationPrincipal User user){
        return new ResponseEntity<>(postService.createPost(requestDto,user), HttpStatus.CREATED);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id,
                                           @Valid @RequestBody PostRequestDto postNew,
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

    @GetMapping("/{idPost}/comments")
    public ResponseEntity<List<CommentResponseDto>> findAllByPost(@PathVariable Long idPost){
        return ResponseEntity.ok(commentService.findAllByPostId(idPost));
    }

    @PostMapping("/{idPost}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long idPost,
                                                 @Valid @RequestBody CommentRequestDto comment,
                                                 @AuthenticationPrincipal User user){
        return new ResponseEntity<>(commentService.createCommentByPost(idPost,comment,user),HttpStatus.CREATED);
    }
}
