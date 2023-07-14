package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

import java.util.List;

@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
public class GenreDaoTest {

    @Autowired
    private GenreDao genreDao;


    @Test
    public void test(){
        {
            Genre genre = genreDao.createGenre("testGenre");
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
