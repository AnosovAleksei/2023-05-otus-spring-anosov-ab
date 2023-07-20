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
import ru.otus.domain.Book;
import ru.otus.domain.Commentary;

import java.util.List;

@DisplayName("Проверка работы CommentaryDaoJpa")
@DataJpaTest
@Import({BookDaoJpa.class, CommentaryDaoJpa.class})
public class CommentaryDaoJpaTest {

    @Autowired
    BookDaoJpa bookDaoJpa;

    @Autowired
    CommentaryDaoJpa commentaryDaoJpa;

    @DisplayName("Проверка create")
    @Test
    public void testCreate(){
        Book book = bookDaoJpa.getBookById(1L);
        final String str = "any massege";
        Commentary commentary = commentaryDaoJpa.create(book.getId(), str);
        Assertions.assertNotNull(commentary);
        Assertions.assertEquals(commentary.getBookId(), book.getId());
        Assertions.assertEquals(commentary.getMessage(), str);
    }

    @DisplayName("Проверка read")
    @Test
    public void testRead(){
        Book book = bookDaoJpa.getBookById(1L);
        final String str = "any massege";
        Commentary commentary = commentaryDaoJpa.create(book.getId(), str);

        Commentary commentary2 = commentaryDaoJpa.read(commentary.getId());

        Assertions.assertNotNull(commentary2);
        Assertions.assertEquals(commentary2.getBookId(), book.getId());
        Assertions.assertEquals(commentary2.getMessage(), str);
    }

    @DisplayName("Проверка update")
    @Test
    public void testUpdate(){
        Book book = bookDaoJpa.getBookById(1L);
        final String str = "any massege";
        final String strNew = "update massege";

        Commentary commentary = commentaryDaoJpa.create(book.getId(), str);

        commentary.setMessage(strNew);


        Commentary commentary2 = commentaryDaoJpa.update(commentary);

        Assertions.assertNotNull(commentary2);
        Assertions.assertEquals(commentary2.getBookId(), book.getId());
        Assertions.assertEquals(commentary2.getMessage(), strNew);
    }

    @DisplayName("Проверка delate")
    @Test
    public void testDelate(){
        Book book = bookDaoJpa.getBookById(1L);
        final String str = "any massege";
        final String strNew = "update massege";

        Commentary commentary = commentaryDaoJpa.create(book.getId(), str);
        Long id = commentary.getId();


        String answer =  commentaryDaoJpa.delate(commentary);

        Assertions.assertNotNull(answer);
        Assertions.assertTrue(answer.length()>0);

        Commentary commentary2 = commentaryDaoJpa.read(id);

        Assertions.assertNull(commentary2);

    }

    @DisplayName("Проверка getAllCommentary")
    @Test
    public void testGetAllComentary(){
        Book book = bookDaoJpa.getBookById(1L);
        final String str = "any massege";
        final String strNew = "new massege";

        Commentary commentary1 = commentaryDaoJpa.create(book.getId(), str);
        Commentary commentary2 = commentaryDaoJpa.create(book.getId(), strNew);


        List<Commentary> commentaryList =  commentaryDaoJpa.getAllCommentary();

        Assertions.assertNotNull(commentaryList);
        Assertions.assertTrue(commentaryList.size()>=2);



    }
}
