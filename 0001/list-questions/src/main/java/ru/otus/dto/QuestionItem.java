package ru.otus.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionItem {
    private String question;

    private List<Answer> answerOptions;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("question : ");
        sb.append(question);
        sb.append("\n");



        sb.append("answer options : [");
        sb.append(String.join(", ",  answerOptions.stream().map(s->s.getBody()).toList()));
        sb.append("]");

//        sb.append("correct answer : ");
//        sb.append(String.join(", ",  answerOptions.stream().filter(s->s.isCorrect()).map(s->s.getBody()).toList()));


        return sb.toString();
    }
}
