package ru.otus.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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


    public List<String> getAllCommentary() {
        List<Commentary> commentaryList = commentaryDao.getAllCommentary();
        return new ArrayList<>() {{
            for (Commentary commentary : commentaryList) {
                add(ModelConverter.convertComentaryToStr(commentary));
            }
        }};
    }


    public Commentary create(Long bookId, String message) {
        Book book = bookDao.getBookById(bookId);
        if (book == null) {
            //TODO действия если книги нет
        }
        return commentaryDao.create(bookId, message);

    }

    public Commentary read(Long commentary_id) {
        return commentaryDao.read(commentary_id);
    }

    public Commentary update(Long commentary_id, String msg, Long bookId){
        Commentary commentary = new Commentary();
        commentary.setId(commentary_id);
        commentary.setMessage(msg);
        commentary.setBookId(bookId);
        return commentaryDao.update(commentary);
    }

    public String delate(Long commentary_id){
        Commentary commentary = commentaryDao.read(commentary_id);
        return  commentaryDao.delate(commentary);
    }


}
