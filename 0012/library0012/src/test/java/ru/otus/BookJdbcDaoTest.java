package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.dao.AuthorDaoJpa;
import ru.otus.dao.BookDaoJpa;
import ru.otus.dao.GenreDaoJpa;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

//@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})

@DisplayName("Проверка работы BookJdbcDao")
@DataJpaTest
@Import({BookDaoJpa.class, AuthorDaoJpa.class, GenreDaoJpa.class})
public class BookJdbcDaoTest {

    @Autowired
    BookDaoJpa bookDaoJdbc;

    @Autowired
    AuthorDaoJpa authorDaoJpa;

    @Autowired
    GenreDaoJpa genreDaoJpa;


    @DisplayName("Проверка работы методов доступа к данным (новых)")
    @Test
    public void testGetBookById(){
        Book book = bookDaoJdbc.getBookById(1L);

        Assertions.assertNotNull(book);
    }

    @DisplayName("Проверка работы методов доступа к данным")
    @Test
    public void testCrud() {

        {
            Author author = authorDaoJpa.createAuthor("TestAuthor");
            Genre genre = genreDaoJpa.createGenre("TestGenre");

            Book book = bookDaoJdbc.saveBook("testName", author, genre);


            Assertions.assertNotNull(book.getAuthor().getName());
            Assertions.assertNotNull(book.getGenre().getName());
            Assertions.assertEquals(book.getName(), "testName");

            Author author2 = authorDaoJpa.createAuthor("TestAuthor2");
            Genre genre2 = genreDaoJpa.createGenre("TestGenre2");


            Book book2 = bookDaoJdbc.upgradeBook("testName", author2, genre2);

            Assertions.assertEquals(book2.getAuthor().getName(), "TestAuthor2");
            Assertions.assertEquals(book2.getGenre().getName(), "TestGenre2");
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
