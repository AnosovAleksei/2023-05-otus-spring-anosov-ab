package ru.otus.config;

import ru.otus.dao.QuestionDao;

public interface QuestionProvider {
    QuestionDao getQuestionDao();
}
