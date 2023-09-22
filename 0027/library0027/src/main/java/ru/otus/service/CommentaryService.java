package ru.otus.service;


import ru.otus.domain.Commentary;
import java.util.List;

public interface CommentaryService {
    List<Commentary> getAll();


    List<String> getAllForString();

    Commentary create(Commentary commentary);

    Commentary read(String commentaryId);

    Commentary update(Commentary commentary);

    void delete(Commentary commentary);

    List<Commentary> getCommentaryByBookId(String bookId);

}
