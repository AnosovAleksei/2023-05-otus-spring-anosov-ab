package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.dto.AuthorCreateDto;
import ru.otus.dto.AuthorUpdateDto;
import ru.otus.service.AuthorService;
import ru.otus.service.DataAlreadyExistsException;
import ru.otus.service.NotFoundException;

import java.util.List;


@DisplayName("Проверка работы AuthorService")
@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;



    @DisplayName("Проверка работы методов доступа к данным - create(AuthorCreateDto)")
    @Test
    public void testCreateDto() {
        Author author = authorService.create(new AuthorCreateDto("testAuthor1_"));
        Assertions.assertEquals(author.getName(), "testAuthor1_");

        DataAlreadyExistsException e = Assertions.assertThrows(DataAlreadyExistsException.class, () ->
                authorService.create(new AuthorCreateDto("testAuthor1_")));
    }

    @DisplayName("Проверка работы методов доступа к данным - update")
    @Test
    public void testUpdate() {
        Author author = authorService.create(new AuthorCreateDto("testAuthor_"));
        author.setName("testAuthor2");
        author = authorService.update(new AuthorUpdateDto(author.getId(),author.getName()));
        Assertions.assertEquals(author.getName(), "testAuthor2");
    }
    @DisplayName("Проверка работы методов доступа к данным - update(AuthorUpdateDto)")
    @Test
    public void testUpdateDto() {



        Author author = authorService.create(new AuthorCreateDto("testAuthor_"));

        AuthorUpdateDto authorUpdateDto = new AuthorUpdateDto(author.getId(), author.getName());


        authorUpdateDto.setName("testAuthor2_");
        author = authorService.update(authorUpdateDto);
        Assertions.assertEquals(author.getName(), "testAuthor2_");
    }

    @DisplayName("Проверка работы методов доступа к данным - read")
    @Test
    public void testRead() {
        authorService.create(new AuthorCreateDto("testAuthor"));
        Author author = authorService.read("testAuthor");
        Assertions.assertEquals(author.getName(), "testAuthor");
    }

    @DisplayName("Проверка работы методов доступа к данным - getAll")
    @Test
    @Transactional
    public void testGetAll() {
        Author author = authorService.create(new AuthorCreateDto("testAuthor1__"));
        Assertions.assertEquals(author.getName(), "testAuthor1__");

        Iterable<Author> authors = authorService.getAll();
        for (Author a : authors) {
            if (a.getId() == author.getId()) {
                Assertions.assertEquals(a.getName(), "testAuthor1__");
            }
        }
    }
}
