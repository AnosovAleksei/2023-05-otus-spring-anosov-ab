package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

import java.util.List;


@DisplayName("Проверка работы AuthorDao")
@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
public class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;


    @DisplayName("Проверка работы методов доступа к данным")
    @Test
    @Transactional
    public void test() {
        {
            Author author = authorDao.create(new Author("testAuthor"));

            Assertions.assertEquals(author.getName(), "testAuthor");
        }
        {
            Author author = authorDao.create(new Author("testAuthor1"));
            Assertions.assertEquals(author.getName(), "testAuthor1");

            List<Author> authors = authorDao.getAll();
            for (Author a : authors) {
                if (a.getId() == author.getId()) {
                    Assertions.assertEquals(a.getName(), "testAuthor1");
                }
            }
        }
        {

            RuntimeException e = Assertions.assertThrows(RuntimeException.class, () ->
                    authorDao.create(new Author("testAuthor")));

        }

    }


}
