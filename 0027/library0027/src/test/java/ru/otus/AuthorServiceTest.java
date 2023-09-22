package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.service.AuthorService;

import java.util.List;


@DisplayName("Проверка работы AuthorDao")
@SpringBootTest(properties = {"spring.shell.interactive.enabled=false", "spring.batch.job.enabled=false"})
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @DisplayName("Проверка работы методов доступа к данным - create")
    @Test
    public void testCreate() {
        Author author = authorService.create(new Author("testAuthor1"));
        Assertions.assertEquals(author.getName(), "testAuthor1");
    }

    @DisplayName("Проверка работы методов доступа к данным - update")
    @Test
    public void testUpdate() {
        Author author = authorService.create(new Author("testAuthor"));
        author.setName("testAuthor2");
        author = authorService.update(author);
        Assertions.assertEquals(author.getName(), "testAuthor2");
    }

    @DisplayName("Проверка работы методов доступа к данным - read")
    @Test
    public void testRead() {
        authorService.create(new Author("testAuthor"));
        Author author = authorService.read("testAuthor");
        Assertions.assertEquals(author.getName(), "testAuthor");
    }

    @DisplayName("Проверка работы методов доступа к данным - getAll")
    @Test
    public void testGetAll() {
        Author author = authorService.create(new Author("testAuthor1"));
        Assertions.assertEquals(author.getName(), "testAuthor1");

        List<Author> authors = authorService.getAll();
        for (Author a : authors) {
            if (a.getId() == author.getId()) {
                Assertions.assertEquals(a.getName(), "testAuthor1");
            }
        }
    }
}
