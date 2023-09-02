package ru.otus.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentaryUpdateRequestDto {

    @NotNull(message = "не может быть null")
    @NotEmpty(message = "не должно быть пустым")
    private String bookId;

    @NotEmpty(message = "не может быть пустым")
    @NotNull(message = "не может быть null")
    private String message;
}
