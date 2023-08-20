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
import ru.otus.dto.AuthorCreateDto;
import ru.otus.dto.BookCreateDto;
import ru.otus.dto.BookDto;
import ru.otus.dto.BookUpdateDto;
import ru.otus.dto.CommentaryCreateDto;
import ru.otus.dto.GenreCreateDto;
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
        Author author = authorService.create(new AuthorCreateDto("Author1"));
        Genre genre = genreService.create(new GenreCreateDto("Genre1"));

        BookCreateDto bookCreateDto = new BookCreateDto("bookName1", author.getId(), genre.getId());


        BookDto book = bookService.create(bookCreateDto);


        List<Commentary> commentaryList = new ArrayList<>();


        commentaryList.add(commentaryService.create(new CommentaryCreateDto(book.getId(), "comment one")));


        commentaryList.add(commentaryService.create(new CommentaryCreateDto(book.getId(), "comment two")));


        BookUpdateDto bookUpdateDto =
                new BookUpdateDto(book.getId(), book.getName(), author.getId(), genre.getId());


        BookDto bookNew = bookService.update(bookUpdateDto);
        Assertions.assertNotNull(bookNew);
        Assertions.assertEquals(author.getName(), bookNew.getAuthorName());
        Assertions.assertEquals(genre.getName(), bookNew.getGenreName());

    }

    @DisplayName("Проверка работы методов доступа к данным create(BookCreateDto)")
    @Test
    public void testCreateDto(){
        Author author = authorService.create(new AuthorCreateDto("Author1_"));
        Genre genre = genreService.create(new GenreCreateDto("Genre1_"));


        BookCreateDto bookCreateDto = new BookCreateDto("bookName1_", author.getId(), genre.getId());

        BookDto book = bookService.create(bookCreateDto);


        List<Commentary> commentaryList = new ArrayList<>();


        commentaryList.add(commentaryService.create(new CommentaryCreateDto(book.getId(), "comment one")));


        commentaryList.add(commentaryService.create(new CommentaryCreateDto(book.getId(), "comment two")));

        BookUpdateDto bookUpdateDto =
                new BookUpdateDto(book.getId(), book.getName(),author.getId(), genre.getId());


        BookDto bookNew = bookService.update(bookUpdateDto);
        Assertions.assertNotNull(bookNew);
        Assertions.assertEquals(author.getName(), bookNew.getAuthorName());
        Assertions.assertEquals(genre.getName(), bookNew.getGenreName());

    }


    @DisplayName("Проверка работы методов доступа к данным delete")
    @Test
    public void testDelate(){
        Author author = authorService.create(new AuthorCreateDto("Author5"));
        Genre genre = genreService.create(new GenreCreateDto("Genre5"));

        BookCreateDto bookCreateDto = new BookCreateDto("bookName5", author.getId(), genre.getId());


        BookDto book = bookService.create(bookCreateDto);
        Long bookId = book.getId();

        List<Commentary> commentaryList = new ArrayList<>();

        commentaryList.add(commentaryService.create(new CommentaryCreateDto(book.getId(), "comment two")));

        commentaryList.add(commentaryService.create(new CommentaryCreateDto(book.getId(), "comment two")));

        Assertions.assertTrue(commentaryService.getCommentaryByBookId(bookId).size()==2);
        bookService.delete(bookId);
        Assertions.assertTrue(commentaryService.getCommentaryByBookId(bookId).size()==0);
    }

    @DisplayName("Проверка работы методов доступа к данным (новых)")
    @Test
    public void testGetBookById(){
        BookDto book = bookService.getByID(1L);
        Assertions.assertNotNull(book);
    }







    @DisplayName("Проверка работы методов доступа к данным")
    @Test
    @Transactional
    public void testCrud() {

        {
            Author author = authorService.create(new AuthorCreateDto("Author"));
            Genre genre = genreService.create(new GenreCreateDto("Genre"));

            BookCreateDto bookCreateDto = new BookCreateDto("testName", author.getId(), genre.getId());

            BookDto book = bookService.create(bookCreateDto);

            Assertions.assertNotNull(book.getAuthorName());
            Assertions.assertNotNull(book.getGenreName());
            Assertions.assertEquals(book.getName(), "testName");

            Author author2 = new Author();
            author2.setName("TestAuthor2");
            author2 = authorService.create(new AuthorCreateDto(author2.getName()));

            Genre genre2 = new Genre();
            genre2.setName("TestGenre2");
            genre2 = genreService.create(new GenreCreateDto(genre2.getName()));

            BookDto book3 = bookService.getByName("testName");

            BookUpdateDto bookUpdateDto =
                    new BookUpdateDto(book3.getId(),"testName", author2.getId(),genre2.getId());



            BookDto book2 = bookService.update(bookUpdateDto);

            Assertions.assertEquals(book2.getAuthorName(), "TestAuthor2");
            Assertions.assertEquals(book2.getGenreName(), "TestGenre2");
            Assertions.assertEquals(book2.getName(), "testName");
        }

        {
            bookService.delete("testName");
            NotFoundException e = Assertions.assertThrows(NotFoundException.class, () ->
                    bookService.getByName("testName"));
        }

    }

}
