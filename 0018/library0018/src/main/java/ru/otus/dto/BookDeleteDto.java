package ru.otus.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class BookDeleteDto {

    @NotNull(message = "не может быть null")
    private Long id;

}
