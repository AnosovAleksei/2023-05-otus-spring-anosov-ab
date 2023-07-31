package ru.otus.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.BookDao;
import ru.otus.dao.CommentaryDao;
import ru.otus.domain.Book;
import ru.otus.domain.Commentary;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentaryService {

    private final CommentaryDao commentaryDao;

    private final BookDao bookDao;


    public List<String> getAll() {
        List<Commentary> commentaryList = commentaryDao.getAll();
        return new ArrayList<>() {{
            for (Commentary commentary : commentaryList) {
                add(ModelConverter.convertComentaryToStr(commentary));
            }
        }};
    }

    @Transactional
    public Commentary create(Long bookId, String message) {
        Book book = bookDao.getById(bookId);
        return commentaryDao.create(book, message);

    }

    @Transactional
    public Commentary read(Long commentaryId) {
        return commentaryDao.read(commentaryId);
    }

    @Transactional
    public Commentary update(Commentary commentary) {
        return commentaryDao.update(commentary);
    }

    @Transactional
    public void delate(Commentary commentary) {
        commentaryDao.delate(commentary);
    }


}
