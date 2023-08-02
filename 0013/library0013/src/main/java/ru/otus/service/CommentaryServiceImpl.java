package ru.otus.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;
import ru.otus.domain.Commentary;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentaryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentaryServiceImpl implements CommentaryService {

    private final CommentaryRepository commentaryRepository;

    private final BookRepository bookRepository;

    @Override
    public List<Commentary> getAll() {
        return commentaryRepository.findAll();
    }


    @Override
    public List<String> getAllForString() {
        List<Commentary> commentaryList = commentaryRepository.findAll();
        return new ArrayList<>() {{
            for (Commentary commentary : commentaryList) {
                add(ModelConverter.convertComentaryToStr(commentary));
            }
        }};
    }

    @Override
    @Transactional
    public Commentary create(Long bookId, String message) {
        Book book = bookRepository.getById(bookId).orElse(null);
        if (book == null) {
            throw new RuntimeException("book with bookId" + bookId.toString() + "does not exist");
        }
        Commentary commentary = new Commentary();
        commentary.setMessage(message);
        commentary.setBookId(book.getId());
        return commentaryRepository.save(commentary);
    }

    @Override
    @Transactional
    public Commentary read(Long commentaryId) {
        return commentaryRepository.findById(commentaryId).orElse(null);
    }

    @Override
    @Transactional
    public Commentary update(Commentary commentary) {
        return commentaryRepository.save(commentary);
    }

    @Override
    @Transactional
    public void delete(Commentary commentary) {
        commentaryRepository.delete(commentary);
    }

    @Override
    public List<Commentary> getCommentaryByBookId(Long bookId) {
        return commentaryRepository.findByBookId(bookId);
    }


}
