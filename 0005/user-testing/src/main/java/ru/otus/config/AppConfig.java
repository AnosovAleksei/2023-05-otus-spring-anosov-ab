package ru.otus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.dao.PersonDao;
import ru.otus.dao.PersonDaoImpl;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionDaoImpl;
import ru.otus.dao.UserInteractionDao;
import ru.otus.dao.UserInteractionDaoImpl;
import ru.otus.service.IOService;
import ru.otus.service.IOServiceImpl;
import ru.otus.service.LocalizationService;
import ru.otus.service.LocalizationServiceImpl;
import ru.otus.service.QuestionService;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties({AppProps.class})
public class AppConfig {


    private final MessageSource messageSource;

    private final AppProps appProps;


    @Bean
    public QuestionDao questionDao(LocalizationService localizationService) {
        return new QuestionDaoImpl(appProps.getFileResourceName());
    }

    @Bean
    public PersonDao personDao(IOService ioService, LocalizationServiceImpl localizationService) {
        return new PersonDaoImpl(ioService, localizationService);
    }

    @Bean
    public UserInteractionDao userInteractionDao(IOService ioService, LocalizationService localizationService) {
        return new UserInteractionDaoImpl(ioService, localizationService);
    }


    @Bean
    public IOService ioService() {
        return new IOServiceImpl(System.out, System.in);
    }

    @Bean
    public QuestionService questionService(QuestionDao questionDao,
                                           PersonDao personDao,
                                           UserInteractionDao userInteractionDao,
                                           IOService ioService,
                                           LocalizationService localizationService) {

        return new QuestionService(questionDao,
                personDao,
                userInteractionDao,
                ioService,
                localizationService,
                appProps.getPassingScore());
    }


    @Bean
    public LocalizationService localizationService() {
        return new LocalizationServiceImpl(messageSource, appProps);
    }
}
