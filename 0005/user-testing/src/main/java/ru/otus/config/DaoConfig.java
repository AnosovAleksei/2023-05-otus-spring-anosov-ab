package ru.otus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.dao.PersonDao;
import ru.otus.dao.PersonDaoImpl;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionDaoImpl;
import ru.otus.service.IOService;
import ru.otus.service.LocalizationService;


@Configuration
public class DaoConfig {

    @Value("${file-data-en}")
    private String fileNameEn;

    @Value("${file-data-ru}")
    private String fileNameRu;

    @Bean
    public QuestionDao questionDao(LocalizationService localizationService) {
        String fileName = fileNameEn;
        if(localizationService.getLocaleConfig().getLocale().toString().equals("ru_RU")){
            fileName = fileNameRu;
        }

        return new QuestionDaoImpl(fileName);
    }

    @Bean
    public PersonDao personDao(IOService ioService, LocalizationService localizationService){
        return new PersonDaoImpl(ioService, localizationService);
    }


}
