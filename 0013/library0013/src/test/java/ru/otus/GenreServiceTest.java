package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Genre;
import ru.otus.service.GenreService;

import java.util.List;

@DisplayName("Проверка работы GenreDao")
@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
public class GenreServiceTest {

    @Autowired
    private GenreService genreService;



    @DisplayName("Проверка работы методов доступа к данным")
    @Transactional
    @Test
    public void test(){
        {
            Genre genre = genreService.create("testGenre");
            Assertions.assertEquals(genre.getName(), "testGenre");
        }
        {
            Genre genre = genreService.create("testGenre1");
            Assertions.assertEquals(genre.getName(), "testGenre1");

            Iterable<Genre> genres = genreService.getAll();
            for(Genre g : genres){
                if(g.getId() == genre.getId()){
                    Assertions.assertEquals(g.getName(), "testGenre1");
                }
            }
        }
    }
}
