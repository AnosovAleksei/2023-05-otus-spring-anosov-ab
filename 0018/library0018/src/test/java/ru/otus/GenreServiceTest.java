package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorCreateDto;
import ru.otus.dto.GenreCreateDto;
import ru.otus.dto.GenreUpdateDto;
import ru.otus.service.DataAlreadyExistsException;
import ru.otus.service.GenreService;

import java.util.List;

@DisplayName("Проверка работы GenreDao")
@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
public class GenreServiceTest {

    @Autowired
    private GenreService genreService;


    @DisplayName("Проверка работы методов доступа к данным - create(GenreCreateDto)")
    @Test
    public void testCreateDto() {
        Genre genre = genreService.create(new GenreCreateDto("testGenre1_"));
        Assertions.assertEquals(genre.getName(), "testGenre1_");

        DataAlreadyExistsException e = Assertions.assertThrows(DataAlreadyExistsException.class, () ->
                genreService.create(new GenreCreateDto("testGenre1_")));
    }

    @DisplayName("Проверка работы методов доступа к данным - update")
    @Test
    public void testUpdate() {
        Genre genre = genreService.create(new GenreCreateDto("testGenre_"));
        genre.setName("testGenre2");
        genre = genreService.update(new GenreUpdateDto(genre.getId(), genre.getName()));
        Assertions.assertEquals(genre.getName(), "testGenre2");
    }

    @DisplayName("Проверка работы методов доступа к данным - update(GenreUpdateDto)")
    @Test
    public void testUpdateDto() {
        Genre genre = genreService.create(new GenreCreateDto("testGenre_"));
        GenreUpdateDto genreUpdateDto = new GenreUpdateDto( genre.getId(), genre.getName());

        genreUpdateDto.setName("testGenre2_");
        genre = genreService.update(genreUpdateDto);
        Assertions.assertEquals(genre.getName(), "testGenre2_");
    }

    @DisplayName("Проверка работы методов доступа к данным - read")
    @Test
    public void testRead() {
        genreService.create(new GenreCreateDto("testGenre"));
        Genre genre = genreService.read("testGenre");
        Assertions.assertEquals(genre.getName(), "testGenre");
    }

    @DisplayName("Проверка работы методов доступа к данным - getAll")
    @Test
    @Transactional
    public void testGetAll() {
        Genre genre = genreService.create(new GenreCreateDto("testGenre1__"));
        Assertions.assertEquals(genre.getName(), "testGenre1__");

        Iterable<Genre> genres = genreService.getAll();
        for(Genre g : genres){
            if(g.getId() == genre.getId()){
                Assertions.assertEquals(g.getName(), "testGenre1__");
            }
        }
    }
}
