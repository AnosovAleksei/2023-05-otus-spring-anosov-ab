package ru.otus.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.dao.UserInteractionDao;
import ru.otus.dao.UserInteractionDaoImpl;
import ru.otus.service.IOService;
import ru.otus.service.LocalizationService;

@Configuration
public class UserInteractionDaoConfig {
    @Bean
    public UserInteractionDao userInteractionDao(IOService ioService, LocalizationService localizationService){
        return new UserInteractionDaoImpl(ioService, localizationService);
    }
}
