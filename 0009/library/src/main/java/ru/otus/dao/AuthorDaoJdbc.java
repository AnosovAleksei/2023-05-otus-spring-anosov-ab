package ru.otus.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AuthorDaoJdbc {

    private final JdbcOperations jdbc;

    public AuthorDaoJdbc(JdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }


    public List<Author> getAllAuthor() {

        return jdbc.query("select * from author order by id", new AuthorMapper());

    }

    public Author getAuthorByName(String name) {
        try {
            return jdbc.queryForObject("select * from author where name = ?", new AuthorMapper(), name);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Author createAuthor(String name) {
        Author author = getAuthorByName(name);

        if (author == null) {
            jdbc.update("""
                    insert into author (name)
                    values (?)""", name);
            return createAuthor(name);
        }
        return author;
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
