package ru.otus.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookCreateDto {

    @NotEmpty(message = "название книги не может быть пустым")
    @NotNull(message = "название книги не может быть null")
    private String name;

    @NotNull(message = "не может быть null")
    @NotEmpty(message = "не должно быть пустым")
    private String authorId;

    @NotNull(message = "не может быть null")
    @NotEmpty(message = "не должно быть пустым")
    private String genreId;
}
