package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;
import ru.otus.service.GenreService;

import java.util.List;

@DisplayName("Проверка работы GenreDao")
@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
public class GenreDaoTest {

    @Autowired
    private GenreDao genreDao;

    @Autowired
    private GenreService genreService;

    @DisplayName("Проверка работы методов доступа к данным")
    @Test
    public void test(){
        {
            Genre genre = genreService.createGenre("testGenre");
            Assertions.assertEquals(genre.getName(), "testGenre");
        }
        {
            Genre genre = genreDao.getGenreByName("testGenre");
            Assertions.assertEquals(genre.getName(), "testGenre");

            List<Genre> genres = genreDao.getAllGenre();
            for(Genre g : genres){
                if(g.getId() == genre.getId()){
                    Assertions.assertEquals(g.getName(), "testGenre");
                }
            }
        }
    }
}
