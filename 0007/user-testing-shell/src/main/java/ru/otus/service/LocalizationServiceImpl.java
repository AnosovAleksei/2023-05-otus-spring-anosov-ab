package ru.otus.service;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.otus.config.LocaleProvider;


@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService{

    private final MessageSource messageSource;


    private final LocaleProvider localeProvider;

    public String getMessage(String key, @Nullable Object[] args){
        return messageSource.getMessage(key, args, localeProvider.getLocale());
    }

    @Override
    public String getMessage(String key) {
        return getMessage(key, null);
    }

}
