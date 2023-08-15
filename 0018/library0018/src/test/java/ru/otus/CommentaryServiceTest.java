package ru.otus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.domain.Book;
import ru.otus.domain.Commentary;
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
        Book book = bookService.getByID(1L);
        final String str = "any massege";

        Commentary commentaryNew = new Commentary();
        commentaryNew.setMessage(str);
        commentaryNew.setBookId(book.getId());

        Commentary commentary = commentaryService.create(commentaryNew);
        Assertions.assertNotNull(commentary);
        Assertions.assertEquals(commentary.getBookId(), book.getId());
        Assertions.assertEquals(commentary.getMessage(), str);
    }

    @DisplayName("Проверка read")
    @Test
    public void testRead(){
        Book book = bookService.getByID(1L);
        final String str = "any massege";

        Commentary commentaryNew = new Commentary();
        commentaryNew.setMessage(str);
        commentaryNew.setBookId(book.getId());

        Commentary commentary = commentaryService.create(commentaryNew);

        Commentary commentary2 = commentaryService.read(commentary.getId());

        Assertions.assertNotNull(commentary2);
        Assertions.assertEquals(commentary2.getBookId(), book.getId());
        Assertions.assertEquals(commentary2.getMessage(), str);
    }

    @DisplayName("Проверка update")
    @Test
    public void testUpdate(){
        Book book = bookService.getByID(1L);
        final String str = "any massege";
        final String strNew = "update massege";

        Commentary commentaryNew = new Commentary();
        commentaryNew.setMessage(str);
        commentaryNew.setBookId(book.getId());

        Commentary commentary = commentaryService.create(commentaryNew);

        commentary.setMessage(strNew);


        Commentary commentary2 = commentaryService.update(commentary);

        Assertions.assertNotNull(commentary2);
        Assertions.assertEquals(commentary2.getBookId(), book.getId());
        Assertions.assertEquals(commentary2.getMessage(), strNew);
    }

    @DisplayName("Проверка delate")
    @Test
    public void testDelate(){
        Book book = bookService.getByID(1L);
        final String str = "any massege";
        final String strNew = "update massege";

        Commentary commentaryNew = new Commentary();
        commentaryNew.setMessage(str);
        commentaryNew.setBookId(book.getId());



        Commentary commentary = commentaryService.create(commentaryNew);
        Long id = commentary.getId();


        commentaryService.delete(commentary);

        Commentary commentary2 = commentaryService.read(id);

        Assertions.assertNull(commentary2);

    }

    @DisplayName("Проверка getAllCommentary")
    @Test
    public void testGetAllComentary(){
        Book book = bookService.getByID(1L);
        final String str = "any massege";
        final String strNew = "new massege";

        Commentary commentaryNew = new Commentary();
        commentaryNew.setMessage(str);
        commentaryNew.setBookId(book.getId());

        Commentary commentary1 = commentaryService.create(commentaryNew);

        commentaryNew.setId(null);
        commentaryNew.setMessage(strNew);
        Commentary commentary2 = commentaryService.create(commentaryNew);


        Iterable<Commentary> commentaryIter =  commentaryService.getAll();

        List<Commentary> commentaryList = new ArrayList<>();
        commentaryIter.forEach(commentaryList::add);


        Assertions.assertNotNull(commentaryList);
        Assertions.assertTrue(commentaryList.size()>=2);



    }
}
