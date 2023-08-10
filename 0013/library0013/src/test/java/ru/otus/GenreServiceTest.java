package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;
import ru.otus.service.GenreService;

import java.util.List;

@DisplayName("Проверка работы GenreDao")
@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
public class GenreServiceTest {

    @Autowired
    private GenreService genreService;

    @DisplayName("Проверка работы методов доступа к данным - create")
    @Test
    public void testCreate() {
        Genre genre = genreService.create(new Genre("testGenre1"));
        Assertions.assertEquals(genre.getName(), "testGenre1");
    }

    @DisplayName("Проверка работы методов доступа к данным - update")
    @Test
    public void testUpdate() {
        Genre genre = genreService.create(new Genre("testGenre"));
        genre.setName("testGenre2");
        genre = genreService.update(genre);
        Assertions.assertEquals(genre.getName(), "testGenre2");
    }

    @DisplayName("Проверка работы методов доступа к данным - read")
    @Test
    public void testRead() {
        genreService.create(new Genre("testGenre"));
        Genre genre = genreService.read("testGenre");
        Assertions.assertEquals(genre.getName(), "testGenre");
    }

    @DisplayName("Проверка работы методов доступа к данным - getAll")
    @Test
    @Transactional
    public void testGetAll() {
        Genre genre = genreService.create(new Genre("testGenre1"));
        Assertions.assertEquals(genre.getName(), "testGenre1");

        Iterable<Genre> genres = genreService.getAll();
        for(Genre g : genres){
            if(g.getId() == genre.getId()){
                Assertions.assertEquals(g.getName(), "testGenre1");
            }
        }
    }
}
