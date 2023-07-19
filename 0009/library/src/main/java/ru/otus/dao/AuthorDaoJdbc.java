package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcTemplate;

    @Override
    public List<Author> getAllAuthor() {

        return namedParameterJdbcTemplate.query("select * from author order by author_id", new AuthorMapper());

    }

    @Override
    public Author getAuthorByName(String name) {
        String sql = "select * from author where name = :name";

        SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);
        List<Author> tempList = this.namedParameterJdbcTemplate.query(sql, namedParameters, new AuthorMapper());

        return tempList != null && tempList.size() > 0 ? tempList.get(0) : null;
    }

    @Override
    public void createAuthor(String name) {
        String sql = "insert into author (name) values (:name)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);
        this.namedParameterJdbcTemplate.update(sql, namedParameters);
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("author_id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
