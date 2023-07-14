package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class GenreDaoJdbc implements GenreDao {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Genre> getAllGenre() {
        return namedParameterJdbcTemplate.query("select * from genre order by id", new GenreMapper());
    }

    @Override
    public Genre getGenreByName(String name) {
        String sql = "select * from genre where name = :name";

        SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);
        List<Genre> tempList = this.namedParameterJdbcTemplate.query(sql, namedParameters, new GenreMapper());

        return tempList != null && tempList.size() > 0 ? tempList.get(0) : null;
    }

    @Override
    public Genre createGenre(String name) {
        Genre genre = getGenreByName(name);

        if (genre == null) {
            String sql = "insert into genre (name) values (:name)";

            SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);
            this.namedParameterJdbcTemplate.update(sql, namedParameters);

            return createGenre(name);
        }
        return genre;
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
