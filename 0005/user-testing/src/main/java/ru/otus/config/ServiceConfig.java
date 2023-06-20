package ru.otus.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.dao.PersonDao;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.UserInteractionDao;
import ru.otus.service.IOService;
import ru.otus.service.IOServiceImpl;
import ru.otus.service.LocalizationService;
import ru.otus.service.QuestionService;

import java.util.Locale;


//@PropertySource("classpath:application.properties")

//@ConfigurationProperties(prefix = "application")
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(LocaleConfig.class)
public class ServiceConfig {

    @Value("${passing-score}")
    private int passingScore;


    @Bean
    public IOService ioService(){
        return new IOServiceImpl(System.out, System.in);
    }

    @Bean
    public QuestionService questionService(QuestionDao questionDao,
                                           PersonDao personDao,
                                           UserInteractionDao userInteractionDao,
                                           IOService ioService,
                                           LocalizationService localizationService){

        return new QuestionService(questionDao, personDao, userInteractionDao, ioService, localizationService, passingScore);
    }

    private final MessageSource messageSource;

    private final LocaleConfig localeConfig;

    @Bean
    public LocalizationService localizationService(){
        return new LocalizationService(messageSource, localeConfig);
    }
}
