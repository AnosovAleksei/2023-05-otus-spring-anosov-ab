package service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.*;
import ru.otus.config.DaoConfig;
import ru.otus.config.ServiceConfig;
import ru.otus.dao.PersonDao;
import ru.otus.dao.UserInteractionDao;
import ru.otus.dto.Person;
import ru.otus.dto.QuestionItem;
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

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        context.registerBean("personDao", PersonDaoImplTest.class);

        context.register(DaoConfig.class);
        context.registerBean("userInteractionDao", UserInteractionDaoImplTrue.class);

        context.register(ServiceConfig.class);
        context.refresh();

        QuestionService questionService = context.getBean(QuestionService.class);

        Assertions.assertTrue(questionService.userTesting());


    }

    @Test
    public void testFalseWork(){

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();

        context.registerBean("personDao", PersonDaoImplTest.class);
        context.register(DaoConfig.class);
        context.registerBean("userInteractionDao", UserInteractionDaoImplFalse.class);

        context.register(ServiceConfig.class);
        context.refresh();

        QuestionService questionService = context.getBean(QuestionService.class);

        Assertions.assertFalse(questionService.userTesting());


    }
}
