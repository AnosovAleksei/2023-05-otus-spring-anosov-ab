package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Commentary;
import ru.otus.domain.Genre;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.CommentaryService;
import ru.otus.service.GenreService;

import java.util.ArrayList;
import java.util.List;


@DisplayName("Проверка работы BookJdbcDao")
@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
public class BookServiceTest {

    @Autowired
    BookService bookService;

    @Autowired
    AuthorService authorService;

    @Autowired
    GenreService genreService;

    @Autowired
    CommentaryService commentaryService;

    @DisplayName("Проверка работы методов доступа к данным count")
    @Test
    public void testCount(){

        Assertions.assertTrue(bookService.count() > 0);
    }

    @DisplayName("Проверка работы методов доступа к данным create")
    @Test
    public void testCreate(){

        Book book = bookService.create("bookName", "Author", "Genre");

        Author author = book.getAuthor();
        Genre genre = book.getGenre();

        List<Commentary> commentaryList = new ArrayList<>();
        commentaryList.add(commentaryService.create(book.getId(), "comment one"));
        commentaryList.add(commentaryService.create(book.getId(), "comment two"));

//        book.setCommentaryList(commentaryList);


        Book bookNew = bookService.update(book);
        bookNew = bookNew;
        Assertions.assertNotNull(bookNew);
//        Assertions.assertEquals(bookNew.getCommentaryList().size(), commentaryList.size());
        Assertions.assertEquals(author.getId(), bookNew.getAuthor().getId());
        Assertions.assertEquals(genre.getId(), bookNew.getGenre().getId());

    }


    @DisplayName("Проверка работы методов доступа к данным delete")
    @Test
    public void testDelate(){

        Book book = bookService.create("bookName5", "Author5", "Genre5");
        Author author = book.getAuthor();
        Genre genre = book.getGenre();

        List<Commentary> commentaryList = new ArrayList<>();
        commentaryList.add(commentaryService.create(book.getId(), "comment one"));
        commentaryList.add(commentaryService.create(book.getId(), "comment two"));

        Assertions.assertTrue(commentaryService.getCommentaryByBookId(book.getId()).size()==2);

        Book bookNew = bookService.update(book);
        bookNew = bookNew;
        Assertions.assertNotNull(bookNew);

        Assertions.assertEquals(author.getId(), bookNew.getAuthor().getId());
        Assertions.assertEquals(genre.getId(), bookNew.getGenre().getId());

        bookService.delete(bookNew.getName());

        Assertions.assertTrue(commentaryService.getCommentaryByBookId(book.getId()).size()==0);




    }

    @DisplayName("Проверка работы методов доступа к данным (новых)")
    @Test
    public void testGetBookById(){
        Book book = bookService.getByID(1L);

        Assertions.assertNotNull(book);
    }







    @DisplayName("Проверка работы методов доступа к данным")
    @Test
    public void testCrud() {

        {
            Book book = bookService.create("testName", "TestAuthor", "TestGenre");
            Author author = book.getAuthor();
            Genre genre = book.getGenre();

            Assertions.assertNotNull(book.getAuthor().getName());
            Assertions.assertNotNull(book.getGenre().getName());
            Assertions.assertEquals(book.getName(), "testName");

            Author author2 = new Author();
            author2.setName("TestAuthor2");
            author2 = authorService.create(author2.getName());

            Genre genre2 = new Genre();
            genre2.setName("TestGenre2");
            genre2 = genreService.create(genre2.getName());

            Book book2 = bookService.getByName("testName");
            book2.setName("testName");
            book2.setGenre(genre2);
            book2.setAuthor(author2);
            book2 = bookService.update(book2);

            Assertions.assertEquals(book2.getAuthor().getName(), "TestAuthor2");
            Assertions.assertEquals(book2.getGenre().getName(), "TestGenre2");
            Assertions.assertEquals(book2.getName(), "testName");
        }

        {
            bookService.delete("testName");
            RuntimeException e = Assertions.assertThrows(RuntimeException.class, () ->
                    bookService.getByName("testName"));
        }

    }

}
