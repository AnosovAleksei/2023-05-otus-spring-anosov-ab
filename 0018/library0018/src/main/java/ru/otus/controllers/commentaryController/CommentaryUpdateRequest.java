package ru.otus.controllers.commentaryController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CommentaryUpdateRequest {

    private Long id;

    private Long bookId;

    private String message;
}
