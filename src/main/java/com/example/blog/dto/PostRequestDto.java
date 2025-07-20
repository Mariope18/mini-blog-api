package com.example.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Questo record rappresenta i dati necessari per creare o aggiornare un post.
// È il posto perfetto per mettere le annotazioni di validazione!
public record PostRequestDto(
        @NotBlank(message = "Il titolo non può essere vuoto")
        @Size(min = 3, message = "Il titolo deve avere almeno 3 caratteri")
        String title,

        @NotBlank(message = "Il contenuto non può essere vuoto")
        String content
) {}