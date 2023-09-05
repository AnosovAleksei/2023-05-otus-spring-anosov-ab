package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.domain.Commentary;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentaryDto {

    private String id;

    private String bookId;

    private String message;

    public CommentaryDto(Commentary commentary) {
        id = commentary.getId();
        bookId = commentary.getBook().getId();
        message = commentary.getMessage();
    }
}
