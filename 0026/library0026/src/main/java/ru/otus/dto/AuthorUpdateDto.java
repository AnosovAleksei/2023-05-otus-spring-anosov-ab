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
public class AuthorUpdateDto {

    @NotNull(message = "не должно быть null")
    private Long id;

    @NotEmpty(message = "не должно быть пустым")
    @NotNull(message = "не должно быть null")
    private String name;
}
