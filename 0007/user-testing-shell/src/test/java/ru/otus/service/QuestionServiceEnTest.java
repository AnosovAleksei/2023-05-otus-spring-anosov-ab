package ru.otus.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest(properties = {"spring.shell.interactive.enabled=false",
                              "application.locale=en_EN",
                              "application.passing-score=1",
                              "application.file-data-en=data_en.csv"})
@DisplayName("Проверка работы сервиса тестирования тестирование (en_EN")
public class QuestionServiceEnTest {

    @MockBean
    IOService ioService;

    @Autowired
    QuestionService questionService;

    @DisplayName("Проверка что тестирование пользователя прошло успешно")
    @Test
    public void testTrueWork(){

        Mockito.when(ioService.readLine("question: 6+3 answer options [9, 7, 6, 5, 4, 3]")).thenReturn("9");
        Mockito.when(ioService.readLine("question: 7+1 answer.options [8, 6, 5, 4, 3, 1]")).thenReturn("8");

        Assertions.assertTrue(questionService.userTesting());
    }

    @DisplayName("Проверка пользователь не смог пройти тестирование")
    @Test
    public void testFalseWork(){

        Mockito.when(ioService.readLine("question: 6+3 answer options [9, 7, 6, 5, 4, 3]")).thenReturn("7");
        Mockito.when(ioService.readLine("question: 7+1 answer.options [8, 6, 5, 4, 3, 1]")).thenReturn("6");

        Assertions.assertFalse(questionService.userTesting());
    }
}
