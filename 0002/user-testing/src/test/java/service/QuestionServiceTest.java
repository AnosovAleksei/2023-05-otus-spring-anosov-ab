package service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.dao.PersonDao;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionDaoImpl;
import ru.otus.dao.UserInteractionDao;
import ru.otus.dto.Person;
import ru.otus.dto.QuestionItem;
import ru.otus.service.IOService;
import ru.otus.service.QuestionService;


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


public class QuestionServiceTest {


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
                1
                );

        Assertions.assertTrue(questionService.userTesting());


    }

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
                1
        );

        Assertions.assertFalse(questionService.userTesting());

    }
}
