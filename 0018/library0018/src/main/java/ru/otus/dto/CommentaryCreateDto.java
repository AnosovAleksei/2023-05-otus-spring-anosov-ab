package ru.otus.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentaryCreateDto {

    @NotNull(message = "не может быть null")
    private Long bookId;

    @NotEmpty(message = "не может быть пустым")
    @NotNull(message = "не может быть null")
    private String message;
}
