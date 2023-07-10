package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.dao.BookDaoJdbc;
import ru.otus.domain.Book;

@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
public class BookJdbcDaoTest {

    @Autowired
    BookDaoJdbc bookDaoJdbc;


    @Test
    public void testCrud() {

        {
            Book book = bookDaoJdbc.createNewBook("testName", "testAuthor", "testGenre");


            Assertions.assertEquals(book.getAuthor().getName(), "testAuthor");
            Assertions.assertEquals(book.getGenre().getName(), "testGenre");
            Assertions.assertEquals(book.getName(), "testName");
        }
        {
            Book book = bookDaoJdbc.getBookByName("testName");

            Assertions.assertEquals(book.getAuthor().getName(), "testAuthor");
            Assertions.assertEquals(book.getGenre().getName(), "testGenre");
            Assertions.assertEquals(book.getName(), "testName");
        }
        {
            Book book = bookDaoJdbc.updateBook("testName", "testAuthor1", "testGenre1");

            Assertions.assertEquals(book.getAuthor().getName(), "testAuthor1");
            Assertions.assertEquals(book.getGenre().getName(), "testGenre1");
            Assertions.assertEquals(book.getName(), "testName");
        }

        {
            String msg = bookDaoJdbc.delateBook("testName");

            Assertions.assertEquals(msg, "book : testName deleted successfully");

            Book book = bookDaoJdbc.getBookByName("testName");

            Assertions.assertNull(book);
        }

    }

}
