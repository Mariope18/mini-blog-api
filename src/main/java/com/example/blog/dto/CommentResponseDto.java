package com.example.blog.dto;

import java.time.LocalDate;

public record CommentResponseDto(
        Long id,
        String text,
        LocalDate creationDate,
        String authorCommentUsername,
        String titlePost,
        String authorPostUsername
) { }
