package ru.otus.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.dao.UserInteractionDao;
import ru.otus.dao.UserInteractionDaoImpl;

@Configuration
public class UserInteractionDaoConfig {
    @Bean
    public UserInteractionDao userInteractionDao(){
        return new UserInteractionDaoImpl();
    }
}
