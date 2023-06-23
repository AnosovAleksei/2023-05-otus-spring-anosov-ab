package ru.otus.service;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import ru.otus.config.LocaleProvider;


@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService{

    private final MessageSource messageSource;


    private final LocaleProvider localeProvider;

    public String getMessage(String key, @Nullable Object[] args){
        return messageSource.getMessage(key, args, localeProvider.getLocale());
    }

}
