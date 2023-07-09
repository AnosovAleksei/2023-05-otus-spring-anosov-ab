package ru.otus.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.dto.Author;
import ru.otus.dto.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDaoJdbc{

    private final JdbcOperations jdbc;

    public BookDaoJdbc(JdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    public int count() {
        Integer count = jdbc.queryForObject("select count(*) from book", Integer.class);
        return count == null ? 0 : count;
    }

    public List<Author> getAllAuthor(){

        return jdbc.query("select * from author order by id", new AuthorMapper());

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
