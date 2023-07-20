package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.dao.AuthorDaoJpa;
import ru.otus.dao.BookDaoJpa;
import ru.otus.dao.CommentaryDaoJpa;
import ru.otus.dao.GenreDaoJpa;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Commentary;
import ru.otus.domain.Genre;

import java.util.ArrayList;
import java.util.List;

//@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})

@DisplayName("Проверка работы BookJdbcDao")
@DataJpaTest
@Import({BookDaoJpa.class, AuthorDaoJpa.class, GenreDaoJpa.class, CommentaryDaoJpa.class})
public class BookDaoJpaTest {

    @Autowired
    BookDaoJpa bookDaoJdbc;

    @Autowired
    AuthorDaoJpa authorDaoJpa;

    @Autowired
    GenreDaoJpa genreDaoJpa;

    @Autowired
    CommentaryDaoJpa commentaryDaoJpa;

    @DisplayName("Проверка работы методов доступа к данным create")
    @Test
    public void testCreate(){
        Author author = authorDaoJpa.createAuthor("Author");

        Genre genre = genreDaoJpa.createGenre("Genre");

        Book book = bookDaoJdbc.createBook("bookName", author, genre);

        List<Commentary> commentaryList = new ArrayList<>(){{
            add(commentaryDaoJpa.create(book, "comment one"));
            add(commentaryDaoJpa.create(book, "comment two"));
        }};
        book.setCommentaryList(commentaryList);


        Book bookNew = bookDaoJdbc.updateBook(book);
        bookNew = bookNew;
        Assertions.assertNotNull(bookNew);
        Assertions.assertEquals(bookNew.getCommentaryList().size(), commentaryList.size());
        Assertions.assertEquals(author.getId(), bookNew.getAuthor().getId());
        Assertions.assertEquals(genre.getId(), bookNew.getGenre().getId());

    }


    @DisplayName("Проверка работы методов доступа к данным delate")
    @Test
    public void testDelate(){
        Author author = authorDaoJpa.createAuthor("Author");

        Genre genre = genreDaoJpa.createGenre("Genre");

        Book book = bookDaoJdbc.createBook("bookName", author, genre);

        List<Commentary> commentaryList = new ArrayList<>(){{
            add(commentaryDaoJpa.create(book, "comment one"));
            add(commentaryDaoJpa.create(book, "comment two"));
        }};
        book.setCommentaryList(commentaryList);


        Book bookNew = bookDaoJdbc.updateBook(book);
        bookNew = bookNew;
        Assertions.assertNotNull(bookNew);
        Assertions.assertEquals(bookNew.getCommentaryList().size(), commentaryList.size());
        Assertions.assertEquals(author.getId(), bookNew.getAuthor().getId());
        Assertions.assertEquals(genre.getId(), bookNew.getGenre().getId());


        Long commentaryId1 = bookNew.getCommentaryList().get(0).getId();
        Long commentaryId2 = bookNew.getCommentaryList().get(1).getId();

        bookDaoJdbc.delateBook(bookNew.getName());

        Assertions.assertNull(commentaryDaoJpa.read(commentaryId1));
        Assertions.assertNull(commentaryDaoJpa.read(commentaryId2));




    }

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

            Book book = bookDaoJdbc.createBook("testName", author, genre);


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
