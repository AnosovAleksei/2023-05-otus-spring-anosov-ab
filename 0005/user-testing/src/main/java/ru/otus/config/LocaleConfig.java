package ru.otus.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;



@Getter
@ConfigurationProperties(prefix = "application")
public class LocaleConfig {


    private final Locale locale;

    @ConstructorBinding
    public LocaleConfig(Locale locale) {
        this.locale = locale;
    }

}
