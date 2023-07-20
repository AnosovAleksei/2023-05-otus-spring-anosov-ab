package ru.otus.dao;


import ru.otus.domain.Commentary;

import java.util.List;

public interface CommentaryDao {

    Commentary create(Long bookId, String message);

    Commentary read(Long commentary_id);

    Commentary update(Commentary commentary);

    String delate(Commentary commentary);

    List<Commentary> getAllCommentary();
}
