package com.example.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequestDto(
        @NotBlank(message = "Il commento non puo essere vuoto")
        @Size(max = 250, message = "Il commento non puo superare i 250 caratteri")
        String text
) { }
