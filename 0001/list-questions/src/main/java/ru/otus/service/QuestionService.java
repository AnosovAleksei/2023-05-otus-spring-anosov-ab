package ru.otus.service;

import lombok.AllArgsConstructor;
import ru.otus.dao.QuestionDao;
import ru.otus.dto.QuestionItem;

import java.util.List;

@AllArgsConstructor
public class QuestionService {

    private final QuestionDao questionDao;

    public void printQuestions() {

        List<QuestionItem> questionItems = questionDao.getQuestionItems();

        System.out.println("--------questions-----------");
        for (QuestionItem questionItem : questionItems) {

            StringBuilder sb = new StringBuilder();
            sb.append("question : ");
            sb.append(questionItem.getQuestion());
            sb.append("\n");

            sb.append("answer options : [");
            sb.append(String.join(", ", questionItem.getAnswerOptions().stream().map(s -> s.getBody()).toList()));
            sb.append("]");

            System.out.println(sb.toString());
            System.out.println("-------------------");
        }


    }
}
