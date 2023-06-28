package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Answer {

    private String body;

    private boolean isCorrect;

}
