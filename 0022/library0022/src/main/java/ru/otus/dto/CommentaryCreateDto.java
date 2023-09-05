package ru.otus.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentaryCreateDto {

    @NotNull(message = "не может быть null")
    @NotEmpty(message = "не должно быть пустым")
    private String bookId;

    @NotEmpty(message = "не может быть пустым")
    @NotNull(message = "не может быть null")
    private String message;
}
