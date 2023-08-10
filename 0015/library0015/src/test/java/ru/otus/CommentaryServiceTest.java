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

@DisplayName("Проверка работы CommentaryDaoJpa")
@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
public class CommentaryServiceTest {
    @Autowired
    AuthorService authorService;

    @Autowired
    GenreService genreService;

    @Autowired
    BookService bookService;

    @Autowired
    CommentaryService commentaryService;


    @DisplayName("Проверка create")
    @Test
    public void testCreate(){
        Author author = authorService.create(new Author("author"));
        Genre genre = genreService.create(new Genre("genre"));
        Book book = new Book();
        book.setName("bookName");
        book.setAuthor(author);
        book.setGenre(genre);
        bookService.create(book);

        final String str = "any massege";

        Commentary commentary = new Commentary();
        commentary.setBook(book);
        commentary.setMessage(str);

        commentaryService.create(commentary);
        Assertions.assertNotNull(commentary);
        Assertions.assertEquals(commentary.getBook().getId(), book.getId());
        Assertions.assertEquals(commentary.getMessage(), str);
    }

    @DisplayName("Проверка read")
    @Test
    public void testRead(){
        Author author = authorService.create(new Author("author"));
        Genre genre = genreService.create(new Genre("genre"));
        Book book = new Book();
        book.setName("bookName");
        book.setAuthor(author);
        book.setGenre(genre);
        bookService.create(book);



        final String str = "any massege";
        Commentary commentary = commentaryService.create(new Commentary(book, str));

        Commentary commentary2 = commentaryService.read(commentary.getId());

        Assertions.assertNotNull(commentary2);
        Assertions.assertEquals(commentary2.getBook().getId(), book.getId());
        Assertions.assertEquals(commentary2.getMessage(), str);
    }

    @DisplayName("Проверка update")
    @Test
    public void testUpdate(){


        Author author = authorService.create(new Author("author"));
        Genre genre = genreService.create(new Genre("genre"));
        Book book = new Book();
        book.setName("bookName");
        book.setAuthor(author);
        book.setGenre(genre);
        bookService.create(book);


        final String str = "any massege";
        final String strNew = "update massege";

        Commentary commentary = commentaryService.create(new Commentary(book, str));

        commentary.setMessage(strNew);


        Commentary commentary2 = commentaryService.update(commentary);

        Assertions.assertNotNull(commentary2);
        Assertions.assertEquals(commentary2.getBook().getId(), book.getId());
        Assertions.assertEquals(commentary2.getMessage(), strNew);
    }

    @DisplayName("Проверка delate")
    @Test
    public void testDelate(){
        Author author = authorService.create(new Author("author"));
        Genre genre = genreService.create(new Genre("genre"));
        Book book = new Book();
        book.setName("bookName");
        book.setAuthor(author);
        book.setGenre(genre);
        bookService.create(book);


        final String str = "any massege";
        final String strNew = "update massege";

        Commentary commentary = commentaryService.create(new Commentary(book, str));
        String id = commentary.getId();


        commentaryService.delete(commentary);

        Commentary commentary2 = commentaryService.read(id);

        Assertions.assertNull(commentary2);

    }

    @DisplayName("Проверка getAllCommentary")
    @Test
    public void testGetAllComentary(){
        Author author = authorService.create(new Author("author"));
        Genre genre = genreService.create(new Genre("genre"));
        Book book = new Book();
        book.setName("bookName");
        book.setAuthor(author);
        book.setGenre(genre);
        bookService.create(book);

        final String str = "any massege";
        final String strNew = "new massege";

        Commentary commentary1 = commentaryService.create(new Commentary(book, str));
        Commentary commentary2 = commentaryService.create(new Commentary(book, strNew));


        Iterable<Commentary> commentaryIter =  commentaryService.getAll();

        List<Commentary> commentaryList = new ArrayList<>();
        commentaryIter.forEach(commentaryList::add);


        Assertions.assertNotNull(commentaryList);
        Assertions.assertTrue(commentaryList.size()>=2);



    }
}
