package ru.otus.controllers.commentaryController;


import lombok.Data;

@Data
public class CommentaryCreateRequest {
    private Long bookId;

    private String message;
}
