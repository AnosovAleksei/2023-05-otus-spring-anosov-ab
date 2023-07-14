package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.BookService;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@RequiredArgsConstructor
@Repository
public class BookDaoJdbc {

    private final JdbcOperations jdbcOperations;

    private final BookService bookService;

    public int count() {
        Integer count = jdbcOperations.queryForObject("select count(*) from book", Integer.class);
        return count == null ? 0 : count;
    }

    public Book createNewBook(String name, String authorName, String genreName) {
        Author author = bookService.createAuthor(authorName);
        Genre genre = bookService.createGenre(genreName);

        return saveBook(name, author, genre);
    }

    public Book updateBook(String name, String authorName, String genreName) {
        Author author = bookService.createAuthor(authorName);
        Genre genre = bookService.createGenre(genreName);

        return upgradeBook(name, author, genre);
    }

    public String delateBook(String name) {
        jdbcOperations.update("""
                delete from book where name = ?
                """, name);
        return "book : " + name + " deleted successfully";
    }

    private Book upgradeBook(String name, Author author, Genre genre) {
        jdbcOperations.update("""
                update book set author_id=?, genre_id=? where name = ?
                """, author.getId(), genre.getId(), name);
        return getBookByName(name);
    }

    private Book saveBook(String name, Author author, Genre genre) {
        jdbcOperations.update("""
                insert into book (name, author_id, genre_id) values (?, ?, ?)
                """, name, author.getId(), genre.getId());
        return getBookByName(name);
    }


    public Book getBookByName(String name) {
        try {
            return jdbcOperations.queryForObject("""
                    select  b.name,
                                a.name as author, 
                                a.id as author_id,
                                g.name as genre, 
                                g.id as genre_id
                                
                        from book b
                        left join author a on (a.id = b.author_id)
                        left join genre g on (g.id = b.genre_id)
                        where b.name = ?
                    """, new BookMapper(), name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public List<Book> getAllBook() {

        return jdbcOperations.query("""
                select  b.name, 
                        a.name as author, 
                        a.id as author_id,
                        g.name as genre, 
                        g.id as genre_id
                        
                from book b
                left join author a on (a.id = b.author_id)
                left join genre g on (g.id = b.genre_id)
                                    
                order by b.name
                """, new BookMapper());

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
