package ru.otus.dao;

import ru.otus.dto.QuestionItem;

public interface UserInteractionDao {
    boolean askQuestion(QuestionItem questionItem);
}
