package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.dao.QuestionDao;
import ru.otus.dto.QuestionItem;

import java.util.List;




public class Main {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionDao questionDao = context.getBean(QuestionDao.class);
        List<QuestionItem> questionItems = questionDao.getQuestionItems();

        System.out.println("--------questions-----------");
        for(QuestionItem questionItem : questionItems){
            System.out.println(questionItem);
            System.out.println("-------------------");
        }
    }
}