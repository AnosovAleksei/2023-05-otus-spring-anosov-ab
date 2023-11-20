package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StudentResponceDto {
    private Long id;

    private String surname;

    private String name;

    private String patronymic;

    private Boolean status;


}
