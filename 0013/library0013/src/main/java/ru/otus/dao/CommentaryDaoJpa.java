package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;
import ru.otus.domain.Commentary;
import ru.otus.repository.CommentaryRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentaryDaoJpa implements CommentaryDao {

    private final CommentaryRepository commentaryRepository;

    @Override
    public Commentary create(Book book, String message) {
        Commentary commentary = new Commentary();
        commentary.setBook(book);
        commentary.setMessage(message);
        commentaryRepository.save(commentary);
        return commentary;
    }

    @Override
    public Commentary read(Long commentaryId) {
        return commentaryRepository.findById(commentaryId).orElse(null);
    }

    @Override
    public Commentary update(Commentary commentary) {
        commentaryRepository.save(commentary);
        return commentary;
    }

    @Override
    public void delate(Commentary commentary) {
        commentaryRepository.delete(commentary);
    }

    @Override
    public List<Commentary> getAll() {
        return commentaryRepository.findAll();
    }
}
