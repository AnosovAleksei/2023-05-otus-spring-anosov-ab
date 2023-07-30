package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

import java.util.List;


@RequiredArgsConstructor
@Service
public class AuthorService {

    private final AuthorDao authorDao;


    @Transactional
    public Author create(String name) {
        return authorDao.create(new Author(name));
    }

    public List<Author> getAll() {
        return authorDao.getAll();
    }
}
