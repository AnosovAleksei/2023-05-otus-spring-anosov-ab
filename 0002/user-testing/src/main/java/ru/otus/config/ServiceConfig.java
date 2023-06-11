package ru.otus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.dao.PersonDao;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.UserInteractionDao;
import ru.otus.service.QuestionService;


@PropertySource("classpath:application.properties")
@Configuration
public class ServiceConfig {

    @Value("${passing-score}")
    private int passingScore;



    @Bean
    public QuestionService questionService(QuestionDao questionDao,
                                           PersonDao personDao,
                                           UserInteractionDao userInteractionDao){
        return new QuestionService(questionDao, personDao, userInteractionDao, passingScore);
    }
}
