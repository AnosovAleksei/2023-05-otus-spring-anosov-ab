package ru.otus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;



@ConfigurationProperties(prefix = "application")
public class AppProps implements LocaleProvider, TestingProvider, ResourceProvider{

    private final Locale locale;


    private final String fileNameEn;

    private final String fileNameRu;

    private final int passingScore;

    @ConstructorBinding
    public AppProps(Locale locale,
                    String fileDataEn,
                    String fileDataRu,
                    int passingScore) {
        this.fileNameEn = fileDataEn;
        this.fileNameRu = fileDataRu;
        this.passingScore = passingScore;
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public int getPassingScore() {
        return passingScore;
    }

    @Override
    public String getFileResourceName() {
        if(locale.toString().equals("ru_RU")){
            return fileNameRu;
        }
        return fileNameEn;
    }
}
