package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public int count() {
        List<Book> tempList = namedParameterJdbcTemplate.query("select * from book", new BookMapper());
        return tempList != null && tempList.size() > 0 ? tempList.size() : 0;
    }


    @Override
    public String delateBook(String name) {
        String sql = "delete from book where name = :name";
        SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);

        namedParameterJdbcTemplate.update(sql, namedParameters);
        return "book : " + name + " deleted successfully";
    }

    @Override
    public Book upgradeBook(String name, Long author_id, Long genre_id) {

        String sql = "update book set author_id=:author_id, genre_id=:genre_id where name = :name";

        Map<String, Object> namedParameters = new HashMap<>() {{
            put("name", name);
            put("author_id", author_id);
            put("genre_id", genre_id);

        }};
        namedParameterJdbcTemplate.update(sql, namedParameters);
        return getBookByName(name);
    }

    @Override
    public Book saveBook(String name, Long author_id, Long genre_id) {
        String sql = "insert into book (name, author_id, genre_id) values (:name, :author_id, :genre_id)";

        Map<String, Object> namedParameters = new HashMap<>() {{
            put("name", name);
            put("author_id", author_id);
            put("genre_id", genre_id);

        }};
        namedParameterJdbcTemplate.update(sql, namedParameters);
        return getBookByName(name);
    }


    @Override
    public Book getBookByName(String name) {
        String sql = """
                select  b.name,
                                a.name as author, 
                                a.author_id,
                                g.name as genre, 
                                g.genre_id
                                
                        from book b
                        left join author a on (a.author_id = b.author_id)
                        left join genre g on (g.genre_id = b.genre_id)
                        where b.name = :name
                        """;
        SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);

        List<Book> tempList = namedParameterJdbcTemplate.query(sql, namedParameters, new BookMapper());
        return tempList != null && tempList.size() > 0 ? tempList.get(0) : null;
    }


    @Override
    public List<Book> getAllBook() {
        String sql = """
                select  b.name, 
                        a.name as author, 
                        a.author_id,
                        g.name as genre, 
                        g.genre_id
                        
                from book b
                left join author a on (a.author_id = b.author_id)
                left join genre g on (g.genre_id = b.genre_id)
                                    
                order by b.name
                        """;

        return namedParameterJdbcTemplate.query(sql, new BookMapper());
    }


    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            String name = resultSet.getString("name");

            Long authorId = resultSet.getLong("author_id");
            String author = resultSet.getString("author");

            Long genreId = resultSet.getLong("genre_id");
            String genre = resultSet.getString("genre");

            return new Book(name, new Author(authorId, author), new Genre(genreId, genre));
        }
    }
}
