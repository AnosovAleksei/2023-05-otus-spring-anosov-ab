package ru.otus.service;

import org.springframework.lang.Nullable;

public interface LocalizationService {
    String getMessage(String key, @Nullable Object[] args);

    String getMessage(String key);
}
