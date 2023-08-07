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
@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;


    @DisplayName("Проверка работы методов доступа к данным")
    @Test
    @Transactional
    public void test() {
        {
            Author author = authorService.create("testAuthor");

            Assertions.assertEquals(author.getName(), "testAuthor");
        }
        {
            Author author = authorService.create("testAuthor1");
            Assertions.assertEquals(author.getName(), "testAuthor1");

            List<Author> authors = authorService.getAll();
            for (Author a : authors) {
                if (a.getId() == author.getId()) {
                    Assertions.assertEquals(a.getName(), "testAuthor1");
                }
            }
        }
    }


}
