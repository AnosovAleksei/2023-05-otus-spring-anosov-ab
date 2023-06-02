package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.dao.QuestionDao;
import ru.otus.dto.QuestionItem;
import ru.otus.service.QuestionService;

import java.util.List;




public class Main {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService questionService = context.getBean(QuestionService.class);

        questionService.printQuestions();

    }
}