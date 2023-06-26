package ru.otus.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.otus.dao.*;
import ru.otus.dto.Person;
import ru.otus.dto.QuestionItem;


class PersonDaoImplTest implements PersonDao {
    @Override
    public Person getPerson() {
        Person p = new Person();
        p.setName("A");
        p.setSurname("B");
        return p;
    }
}


class localizationServiceTestImpl implements LocalizationService{


    @Override
    public String getMessage(String key, Object[] args) {
        return key;
    }

    @Override
    public String getMessage(String key) {
        return getMessage(key, null);
    }
}

class ioServiceTestTrueImpl implements IOService{
    private String lastPrint;
    @Override
    public void printLn(String line) {
        lastPrint = line;
    }

    @Override
    public String readLine() {
        switch (lastPrint) {
            case "question: 6+3 answer.options [9, 7, 6, 5, 4, 3]":
                return "9";
            case "question: 7+1 answer.options [8, 5, 4, 3, 1]":
                return "8";
        }

        return null;
    }
}

class ioServiceTestFalseImpl implements IOService {

    @Override
    public void printLn(String line) {
    }

    @Override
    public String readLine() {
        return null;
    }
}

@DisplayName("Проверка работы сервиса тестирования тестирование")
public class QuestionServiceTest {

    @DisplayName("Проверка что тестирование пользователя прошло успешно")
    @Test
    public void testTrueWork(){

        QuestionDao questionDao = new QuestionDaoImpl("data.csv");
        PersonDao personDao = new PersonDaoImplTest();
        LocalizationService localizationService = new localizationServiceTestImpl();
        IOService ioService = new ioServiceTestTrueImpl();

        UserInteractionDao userInteractionDao = new UserInteractionDaoImpl(ioService, localizationService);


        QuestionService questionService = new QuestionService(questionDao,
                personDao,
                userInteractionDao,
                ioService,
                localizationService,
                1
                );

        Assertions.assertTrue(questionService.userTesting());


    }

    @DisplayName("Проверка пользователь не смог пройти тестирование")
    @Test
    public void testFalseWork(){

        QuestionDao questionDao = new QuestionDaoImpl("data.csv");
        PersonDao personDao = new PersonDaoImplTest();
        LocalizationService localizationService = new localizationServiceTestImpl();
        IOService ioService = new ioServiceTestFalseImpl();
        UserInteractionDao userInteractionDao = new UserInteractionDaoImpl(ioService, localizationService);


        QuestionService questionService = new QuestionService(questionDao,
                personDao,
                userInteractionDao,
                ioService,
                localizationService,
                1
        );

        Assertions.assertFalse(questionService.userTesting());

    }
}
