package ru.otus.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;



@Getter
@ConfigurationProperties(prefix = "application")
public class LocaleConfig {

    @ConstructorBinding
    public LocaleConfig(Locale locale) {
        this.locale = locale;
    }
    private final Locale locale;
}
