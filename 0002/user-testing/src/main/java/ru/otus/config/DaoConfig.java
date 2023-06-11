package ru.otus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.dao.PersonDao;
import ru.otus.dao.PersonDaoImpl;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionDaoImpl;


@PropertySource("classpath:application.properties")
@Configuration
public class DaoConfig {

    @Value("${file-data}")
    private String fileName;

    @Bean
    public QuestionDao questionDao() {
        return new QuestionDaoImpl(fileName);
    }

    @Bean
    public PersonDao personDao(){
        return new PersonDaoImpl();
    }


}
