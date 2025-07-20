package com.example.blog.mapper;

import com.example.blog.dto.CommentResponseDto;
import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentResponseDto toDto(Comment comment){
        return new CommentResponseDto(
                comment.getId(),
                comment.getText(),
                comment.getCreationDate(),
                comment.getUser().getUsername(),
                comment.getPost().getTitle(),
                comment.getPost().getUser().getUsername()
        );
    }
}
