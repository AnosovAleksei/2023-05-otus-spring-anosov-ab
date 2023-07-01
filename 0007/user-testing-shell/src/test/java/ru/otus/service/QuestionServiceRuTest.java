package ru.otus.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest(properties = {"spring.shell.interactive.enabled=false",
                              "application.locale=ru_RU",
                              "application.passing-score=1",
                              "application.file-data-ru=data_ru.csv"})
@DisplayName("Проверка работы сервиса тестирования тестирование (ru_RU")
public class QuestionServiceRuTest {

    @MockBean
    IOService ioService;

    @Autowired
    QuestionService questionService;

    @DisplayName("Проверка что тестирование пользователя прошло успешно")
    @Test
    public void testTrueWork(){

        Mockito.when(ioService.readLine("вопрос: 6+3 варианты ответа [9, 7, 6, 5, 4, 3]")).thenReturn("9");
        Mockito.when(ioService.readLine("вопрос: 7+1 варианты ответа [8, 6, 5, 4, 3, 1]")).thenReturn("8");

        boolean rez = questionService.userTesting();
        Assertions.assertTrue(rez);
        questionService.printRaportTesting(rez);

        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);

        Mockito.verify(ioService, Mockito.times(1)).printLn(valueCapture.capture());
        valueCapture = valueCapture;
        System.out.println(valueCapture.getAllValues().get(0));
        Assertions.assertEquals(valueCapture.getAllValues().get(0),"пользователь: null null\n" +
                "тест успешно выполнен");
    }

    @DisplayName("Проверка пользователь не смог пройти тестирование")
    @Test
    public void testFalseWork(){

        Mockito.when(ioService.readLine("вопрос: 6+3 варианты ответа [9, 7, 6, 5, 4, 3]")).thenReturn("7");
        Mockito.when(ioService.readLine("вопрос: 7+1 варианты ответа [8, 6, 5, 4, 3, 1]")).thenReturn("6");

        boolean rez = questionService.userTesting();
        Assertions.assertFalse(rez);
        questionService.printRaportTesting(rez);

        ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);

        Mockito.verify(ioService, Mockito.times(1)).printLn(valueCapture.capture());
        valueCapture = valueCapture;
        System.out.println(valueCapture.getAllValues().get(0));
        Assertions.assertEquals(valueCapture.getAllValues().get(0),"пользователь: null null\n" +
                "тест не выполнен");
    }
}
