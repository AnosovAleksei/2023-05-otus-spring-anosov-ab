package ru.otus.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AuthorCreateDto {

    @NotEmpty(message = "Имя автора не может быть пустым")
    @NotNull(message = "Имя автора не может быть null")
    private String name;
}
