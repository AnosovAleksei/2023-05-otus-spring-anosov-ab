package ru.otus.dao;


import ru.otus.domain.Book;
import ru.otus.domain.Commentary;

import java.util.List;

public interface CommentaryDao {

    Commentary create(Book book, String message);

    Commentary read(Long commentaryId);

    Commentary update(Commentary commentary);

    void delate(Commentary commentary);

    List<Commentary> getAll();
}
