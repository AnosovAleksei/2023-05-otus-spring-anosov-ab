package ru.otus.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CommentaryUpdateDto {

    @NotNull(message = "не может быть null")
    private Long id;

    @NotNull(message = "не может быть null")
    private Long bookId;

    @NotEmpty(message = "не может быть пустым")
    @NotNull(message = "не может быть null")
    private String message;
}
