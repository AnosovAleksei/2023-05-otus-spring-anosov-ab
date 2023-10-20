package ru.otus.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookUpdateRequestDto {

    @NotEmpty(message = "название книги не может быть пустым")
    @NotNull(message = "название книги не может быть null")
    private String name;

    @NotNull(message = "не может быть null")
    private Long authorId;


    @NotNull(message = "не может быть null")
    private Long genreId;
}
