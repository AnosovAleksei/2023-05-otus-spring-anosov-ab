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
        if (book == null) {
            //TODO действия если книги нет
        }
        return commentaryDao.create(book, message);

    }

    @Transactional
    public Commentary read(Long commentary_id) {
        return commentaryDao.read(commentary_id);
    }

    @Transactional
    public Commentary update(Long commentary_id, String msg, Long bookId) {
        Book book = bookDao.getById(bookId);

        Commentary commentary = new Commentary();
        commentary.setId(commentary_id);
        commentary.setMessage(msg);
        commentary.setBook(book);
        return commentaryDao.update(commentary);
    }

    @Transactional
    public String delate(Long commentary_id) {
        Commentary commentary = commentaryDao.read(commentary_id);
        return commentaryDao.delate(commentary);
    }


}
