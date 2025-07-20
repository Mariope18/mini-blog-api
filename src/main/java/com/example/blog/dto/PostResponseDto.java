package com.example.blog.dto;

import java.time.LocalDate;

// Questo record rappresenta un post come lo vogliamo mostrare all'esterno.
// Nota: non ha l'oggetto User completo per sicurezza, solo il suo ID o username.
public record PostResponseDto(
        Long id,
        String title,
        String content,
        LocalDate creationDate,
        String authorUsername // Mettiamo solo lo username dell'autore
) {}