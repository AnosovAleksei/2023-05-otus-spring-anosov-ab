package ru.otus.service;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.otus.config.LocaleConfig;

import java.util.Locale;


@RequiredArgsConstructor
public class LocalizationService {

    private final MessageSource messageSource;


    private final LocaleConfig localeConfig;

    public String getMessage(String key, @Nullable Object[] args){
        return messageSource.getMessage(key, args, localeConfig.getLocale());
    }

    public LocaleConfig getLocaleConfig(){
        return localeConfig;
    }

}
