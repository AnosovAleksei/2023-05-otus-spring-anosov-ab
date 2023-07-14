package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Author> getAllAuthor() {

        return namedParameterJdbcTemplate.query("select * from author order by id", new AuthorMapper());

    }

    @Override
    public Author getAuthorByName(String name) {
        String sql = "select * from author where name = :name";

        SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);
        List<Author> tempList = this.namedParameterJdbcTemplate.query(sql, namedParameters, new AuthorMapper());

        return tempList != null && tempList.size() > 0 ? tempList.get(0) : null;
    }

    @Override
    public Author createAuthor(String name) {
        Author author = getAuthorByName(name);

        if (author == null) {
            String sql = "insert into author (name) values (:name)";

            SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);
            this.namedParameterJdbcTemplate.update(sql, namedParameters);

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
