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

    @DisplayName("Проверка работы методов доступа к данным count")
    @Test
    public void testCount(){
//        Author author = new Author();
//        author.setName("Author");
//        author = authorDaoJpa.create(author);
//
//        Genre genre = new Genre();
//        genre.setName("Genre");
//        genre = genreDaoJpa.create(genre);
//
//        Book book = bookDaoJdbc.create("bookName", author, genre);


        Assertions.assertTrue(bookDaoJdbc.count() > 0);
    }

    @DisplayName("Проверка работы методов доступа к данным create")
    @Test
    public void testCreate(){
        Author author = new Author();
        author.setName("Author");
        author = authorDaoJpa.create(author);

        Genre genre = new Genre();
        genre.setName("Genre");
        genre = genreDaoJpa.create(genre);

        Book book = new Book();
        book.setName("bookName");
        book.setGenre(genre);
        book.setAuthor(author);
        book = bookDaoJdbc.create(book);

        List<Commentary> commentaryList = new ArrayList<>();
        commentaryList.add(commentaryDaoJpa.create(book, "comment one"));
        commentaryList.add(commentaryDaoJpa.create(book, "comment two"));

        book.setCommentaryList(commentaryList);


        Book bookNew = bookDaoJdbc.update(book);
        bookNew = bookNew;
        Assertions.assertNotNull(bookNew);
        Assertions.assertEquals(bookNew.getCommentaryList().size(), commentaryList.size());
        Assertions.assertEquals(author.getId(), bookNew.getAuthor().getId());
        Assertions.assertEquals(genre.getId(), bookNew.getGenre().getId());

    }


    @DisplayName("Проверка работы методов доступа к данным delate")
    @Test
    public void testDelate(){
        Author author = new Author();
        author.setName("Author");
        author = authorDaoJpa.create(author);


        Genre genre = new Genre();
        genre.setName("Genre");
        genre = genreDaoJpa.create(genre);


        Book book = new Book();
        book.setName("bookName");
        book.setGenre(genre);
        book.setAuthor(author);
        book = bookDaoJdbc.create(book);

        List<Commentary> commentaryList = new ArrayList<>();
        commentaryList.add(commentaryDaoJpa.create(book, "comment one"));
        commentaryList.add(commentaryDaoJpa.create(book, "comment two"));

        book.setCommentaryList(commentaryList);


        Book bookNew = bookDaoJdbc.update(book);
        bookNew = bookNew;
        Assertions.assertNotNull(bookNew);
        Assertions.assertEquals(bookNew.getCommentaryList().size(), commentaryList.size());
        Assertions.assertEquals(author.getId(), bookNew.getAuthor().getId());
        Assertions.assertEquals(genre.getId(), bookNew.getGenre().getId());


        Long commentaryId1 = bookNew.getCommentaryList().get(0).getId();
        Long commentaryId2 = bookNew.getCommentaryList().get(1).getId();

        bookDaoJdbc.delate(bookNew.getName());

        Assertions.assertNull(commentaryDaoJpa.read(commentaryId1));
        Assertions.assertNull(commentaryDaoJpa.read(commentaryId2));




    }

    @DisplayName("Проверка работы методов доступа к данным (новых)")
    @Test
    public void testGetBookById(){
        Book book = bookDaoJdbc.getById(1L);

        Assertions.assertNotNull(book);
    }






    @DisplayName("Проверка работы методов доступа к данным")
    @Test
    public void testCrud() {

        {
            Author author = new Author();
            author.setName("TestAuthor");
            author = authorDaoJpa.create(author);


            Genre genre = new Genre();
            genre.setName("TestGenre");
            genre = genreDaoJpa.create(genre);

            Book book = new Book();
            book.setName("testName");
            book.setGenre(genre);
            book.setAuthor(author);
            book = bookDaoJdbc.create(book);


            Assertions.assertNotNull(book.getAuthor().getName());
            Assertions.assertNotNull(book.getGenre().getName());
            Assertions.assertEquals(book.getName(), "testName");

            Author author2 = new Author();
            author2.setName("TestAuthor2");
            author2 = authorDaoJpa.create(author2);

            Genre genre2 = new Genre();
            genre2.setName("TestGenre2");
            genre2 = genreDaoJpa.create(genre2);

            Book book2 = new Book();
            book2.setName("testName");
            book2.setGenre(genre2);
            book2.setAuthor(author2);
            book2 = bookDaoJdbc.create(book2);

            Assertions.assertEquals(book2.getAuthor().getName(), "TestAuthor2");
            Assertions.assertEquals(book2.getGenre().getName(), "TestGenre2");
            Assertions.assertEquals(book2.getName(), "testName");
        }

        {
            bookDaoJdbc.delate("testName");

            Book book = bookDaoJdbc.getByName("testName");

            Assertions.assertNull(book);
        }

    }

}
