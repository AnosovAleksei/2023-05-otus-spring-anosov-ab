package ru.otus.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class GenreDaoJdbc {

    private final JdbcOperations jdbc;

    public GenreDaoJdbc(JdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }


    public List<Genre> getAllGenre() {
        return jdbc.query("select * from genre order by id", new GenreMapper());
    }


    public Genre createGenre(String name) {
        try {
            return jdbc.queryForObject("select * from genre where name = ?", new GenreMapper(), name);

        } catch (EmptyResultDataAccessException e) {
            jdbc.update("""
                    insert into genre (name)
                    values (?)""", name);
            return createGenre(name);
        }
    }


    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
