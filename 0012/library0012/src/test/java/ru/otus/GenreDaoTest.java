package ru.otus;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

import java.util.List;

@DisplayName("Проверка работы GenreDao")
@SpringBootTest(properties = {"spring.shell.interactive.enabled=false"})
public class GenreDaoTest {

    @Autowired
    private GenreDao genreDao;



    @DisplayName("Проверка работы методов доступа к данным")
    @Transactional
    @Test
    public void test(){
        {
            Genre genre = genreDao.create(new Genre("testGenre"));
            Assertions.assertEquals(genre.getName(), "testGenre");
        }
        {
            Genre genre = genreDao.getByName("testGenre");
            Assertions.assertEquals(genre.getName(), "testGenre");

            List<Genre> genres = genreDao.getAll();
            for(Genre g : genres){
                if(g.getId() == genre.getId()){
                    Assertions.assertEquals(g.getName(), "testGenre");
                }
            }
        }
        {

            RuntimeException e = Assertions.assertThrows(RuntimeException.class, () ->
                    genreDao.create(new Genre("testGenre")));

        }
    }
}
