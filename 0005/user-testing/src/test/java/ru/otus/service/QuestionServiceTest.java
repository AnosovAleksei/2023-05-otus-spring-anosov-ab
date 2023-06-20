package ru.otus.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.config.LocaleConfig;
import ru.otus.dao.PersonDao;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionDaoImpl;
import ru.otus.dao.UserInteractionDao;
import ru.otus.dto.Person;
import ru.otus.dto.QuestionItem;

import java.util.Locale;


class PersonDaoImplTest implements PersonDao {
    @Override
    public Person getPerson() {
        Person p = new Person();
        p.setName("A");
        p.setSurname("B");
        return p;
    }
}

class IOServiceImplTest implements IOService {

    @Override
    public void printLn(String line) {
        System.out.println(line);
    }

    @Override
    public String readLine() {
        return null;
    }

}
class UserInteractionDaoImplTrue implements UserInteractionDao {
    @Override
    public boolean askQuestion(QuestionItem questionItem){
        return true;
    }
}



class UserInteractionDaoImplFalse implements UserInteractionDao {
    @Override
    public boolean askQuestion(QuestionItem questionItem){
        return false;
    }
}



@DisplayName("Проверка работы сервиса тестирования тестирование")
public class QuestionServiceTest {

    @MockBean
    LocalizationService localizationService;

    @DisplayName("Проверка что тестирование пользователя прошло успешно")
    @Test
    public void testTrueWork(){

        QuestionDao questionDao = new QuestionDaoImpl("data.csv");
        PersonDao personDao = new PersonDaoImplTest();
        UserInteractionDao userInteractionDao = new UserInteractionDaoImplTrue();
        IOService ioService = new IOServiceImplTest();




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
        UserInteractionDao userInteractionDao = new UserInteractionDaoImplFalse();
        IOService ioService = new IOServiceImplTest();



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
