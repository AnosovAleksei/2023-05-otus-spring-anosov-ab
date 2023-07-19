package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.otus.dao.BookDao;
import ru.otus.dao.BookDaoJdbc;
import ru.otus.domain.Book;
import ru.otus.service.BookService;

//@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})

@DisplayName("Проверка работы BookJdbcDao")
@JdbcTest
@Import({BookDaoJdbc.class})
public class BookJdbcDaoTest {

    @Autowired
    BookDaoJdbc bookDaoJdbc;


    @DisplayName("Проверка работы методов доступа к данным")
    @Test
    public void testCrud() {

        {

            bookDaoJdbc.saveBook("testName", 1L, 1L);
            Book book = bookDaoJdbc.getBookByName("testName");


            Assertions.assertNotNull(book.getAuthor().getName());
            Assertions.assertNotNull(book.getGenre().getName());
            Assertions.assertEquals(book.getName(), "testName");

            Book book2 = bookDaoJdbc.upgradeBook("testName", 2L, 2L);

            Assertions.assertNotEquals(book2.getAuthor().getName(), book.getAuthor().getName());
            Assertions.assertNotEquals(book2.getGenre().getName(), book.getGenre().getName());
            Assertions.assertEquals(book2.getName(), "testName");
        }

        {
            String msg = bookDaoJdbc.delateBook("testName");

            Assertions.assertEquals(msg, "book : testName deleted successfully");

            Book book = bookDaoJdbc.getBookByName("testName");

            Assertions.assertNull(book);
        }

    }

}
