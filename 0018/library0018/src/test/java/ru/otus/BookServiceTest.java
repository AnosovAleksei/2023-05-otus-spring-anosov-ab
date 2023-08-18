package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Commentary;
import ru.otus.domain.Genre;
import ru.otus.dto.BookCreateDto;
import ru.otus.dto.BookUpdateDto;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.CommentaryService;
import ru.otus.service.GenreService;
import ru.otus.service.NotFoundException;

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
        Author author = authorService.create(new Author("Author1"));
        Genre genre = genreService.create(new Genre("Genre1"));
        Book book = new Book();
        book.setName("bookName1");
        book.setAuthor(author);
        book.setGenre(genre);




        book = bookService.create(book);

        Commentary commentaryNew = new Commentary();
        commentaryNew.setBookId(book.getId());

        List<Commentary> commentaryList = new ArrayList<>();

        commentaryNew.setId(null);
        commentaryNew.setMessage("comment one");
        commentaryList.add(commentaryService.create(commentaryNew));

        commentaryNew.setId(null);
        commentaryNew.setMessage("comment two");
        commentaryList.add(commentaryService.create(commentaryNew));

        Book bookNew = bookService.update(book);
        Assertions.assertNotNull(bookNew);
        Assertions.assertEquals(author.getId(), bookNew.getAuthor().getId());
        Assertions.assertEquals(genre.getId(), bookNew.getGenre().getId());

    }

    @DisplayName("Проверка работы методов доступа к данным create(BookCreateDto)")
    @Test
    public void testCreateDto(){
        Author author = authorService.create(new Author("Author1"));
        Genre genre = genreService.create(new Genre("Genre1"));


        BookCreateDto bookCreateDto = new BookCreateDto("bookName1_", author.getId(), genre.getId());

        Book book = bookService.create(bookCreateDto);

        Commentary commentaryNew = new Commentary();
        commentaryNew.setBookId(book.getId());

        List<Commentary> commentaryList = new ArrayList<>();

        commentaryNew.setId(null);
        commentaryNew.setMessage("comment one");
        commentaryList.add(commentaryService.create(commentaryNew));

        commentaryNew.setId(null);
        commentaryNew.setMessage("comment two");
        commentaryList.add(commentaryService.create(commentaryNew));


        Book bookNew = bookService.update(book);
        Assertions.assertNotNull(bookNew);
        Assertions.assertEquals(author.getId(), bookNew.getAuthor().getId());
        Assertions.assertEquals(genre.getId(), bookNew.getGenre().getId());

    }


    @DisplayName("Проверка работы методов доступа к данным delete")
    @Test
    public void testDelate(){
        Author author = authorService.create(new Author("Author5"));
        Genre genre = genreService.create(new Genre("Genre5"));
        Book book = new Book();
        book.setName("bookName5");
        book.setAuthor(author);
        book.setGenre(genre);


        bookService.create(book);
        Long bookId = book.getId();

        List<Commentary> commentaryList = new ArrayList<>();
        {
            Commentary commentaryNew = new Commentary();
            commentaryNew.setBookId(bookId);
            commentaryNew.setMessage("comment one");
            commentaryList.add(commentaryService.create(commentaryNew));
        }
        {
            Commentary commentaryNew = new Commentary();
            commentaryNew.setBookId(bookId);
            commentaryNew.setMessage("comment two");
            commentaryList.add(commentaryService.create(commentaryNew));
        }
        Assertions.assertTrue(commentaryService.getCommentaryByBookId(bookId).size()==2);
        bookService.delete(bookId);
        Assertions.assertTrue(commentaryService.getCommentaryByBookId(bookId).size()==0);
    }

    @DisplayName("Проверка работы методов доступа к данным (новых)")
    @Test
    public void testGetBookById(){
        Book book = bookService.getByID(1L);
        Assertions.assertNotNull(book);
    }







    @DisplayName("Проверка работы методов доступа к данным")
    @Test
    @Transactional
    public void testCrud() {

        {
            Author author = authorService.create(new Author("Author"));
            Genre genre = genreService.create(new Genre("Genre"));
            Book book = new Book();
            book.setName("testName");
            book.setAuthor(author);
            book.setGenre(genre);

            bookService.create(book);

//            Book book = bookService.create("testName", "TestAuthor", "TestGenre");
//            Author author = book.getAuthor();
//            Genre genre = book.getGenre();

            Assertions.assertNotNull(book.getAuthor().getName());
            Assertions.assertNotNull(book.getGenre().getName());
            Assertions.assertEquals(book.getName(), "testName");

            Author author2 = new Author();
            author2.setName("TestAuthor2");
            author2 = authorService.create(author2);

            Genre genre2 = new Genre();
            genre2.setName("TestGenre2");
            genre2 = genreService.create(genre2);

            Book book3 = bookService.getByName("testName");

            BookUpdateDto bookUpdateDto =
                    new BookUpdateDto(book3.getId(),"testName", author2.getId(),genre2.getId());



            Book book2 = bookService.update(bookUpdateDto);

            Assertions.assertEquals(book2.getAuthor().getName(), "TestAuthor2");
            Assertions.assertEquals(book2.getGenre().getName(), "TestGenre2");
            Assertions.assertEquals(book2.getName(), "testName");
        }

        {
            bookService.delete("testName");
            NotFoundException e = Assertions.assertThrows(NotFoundException.class, () ->
                    bookService.getByName("testName"));
        }

    }

}
