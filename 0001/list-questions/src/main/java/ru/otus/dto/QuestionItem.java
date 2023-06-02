package ru.otus.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionItem {
    private String question;

    private List<Answer> answerOptions;
}
