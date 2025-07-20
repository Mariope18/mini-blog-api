package com.example.blog.mapper;

import com.example.blog.dto.PostResponseDto;
import com.example.blog.model.Post;
import org.springframework.stereotype.Component;

@Component // Lo rendiamo un componente Spring per poterlo iniettare
public class PostMapper {

    public PostResponseDto toDto(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreationDate(),
                post.getUser().getUsername() // Prendiamo solo lo username
        );
    }

    // Non creiamo un metodo toEntity perché lo faremo direttamente nel service,
    // dato che alcuni campi (user, creationDate) devono essere impostati lì.
}