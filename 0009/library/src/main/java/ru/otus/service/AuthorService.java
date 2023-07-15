package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

import java.util.List;


@RequiredArgsConstructor
@Component
public class AuthorService {

    private final AuthorDao authorDao;

    public Author createAuthor(String name) {
        Author author = authorDao.getAuthorByName(name);
        if (author != null) {
            return author;
        }
        authorDao.createAuthor(name);
        author = authorDao.getAuthorByName(name);
        //if(author!=null){
        //TODO add checking what was created;
        //}
        return author;
    }

    public List<Author> getAllAuthor() {
        return authorDao.getAllAuthor();
    }
}
