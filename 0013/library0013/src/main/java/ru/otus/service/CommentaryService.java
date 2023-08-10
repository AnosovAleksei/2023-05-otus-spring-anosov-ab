package ru.otus.service;


import ru.otus.domain.Commentary;
import java.util.List;

public interface CommentaryService {
    List<Commentary> getAll();


    List<String> getAllForString();

    Commentary create(Long bookId, String message);

    Commentary read(Long commentaryId);

    Commentary update(Commentary commentary);

    void delete(Commentary commentary);

    List<Commentary> getCommentaryByBookId(Long bookId);

}
