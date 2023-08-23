package ru.otus.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.controllers.BookController;
import ru.otus.dto.BookCreateDto;
import ru.otus.dto.BookDeleteDto;
import ru.otus.dto.BookDto;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookUpdateDto;
import ru.otus.dto.GenreCreateDto;
import ru.otus.service.AuthorService;
import ru.otus.service.BookService;
import ru.otus.service.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Проверка работы BookController")
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    BookService bookService;

    @MockBean
    AuthorService authorService;
    @MockBean
    GenreService genreService;


    @DisplayName("getAll")
    @Test
    public void testGetBooks(){
        List<Book> bookList = new ArrayList<>(){{
            Author author = new Author("a1");
            author.setId(1L);

            Genre genre1 = new Genre("g1");
            genre1.setId(1L);

            Book book = new Book();
            book.setId(1L);
            book.setAuthor(author);
            book.setGenre(genre1);
            book.setName("zzzzz1");
            add(book);

        }};
        List<BookDto> bookDtoList = new ArrayList<>(){{
            for(Book book: bookList){
                add(new BookDto(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName()));
            }
        }};


        given(bookService.getAll()).willReturn(bookDtoList);

        try {
            mvc.perform(get("/api/v1/book"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", Matchers.hasSize(1)))
                    .andExpect(jsonPath("$[0].id").value(1L))
                    .andExpect(jsonPath("$[0].name").value("zzzzz1"))
                    .andExpect(jsonPath("$[0].authorName").value("a1"))
                    .andExpect(jsonPath("$[0].genreName").value("g1"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("Create")
    @Test
    public void testCreateBook(){
        String str = "bookName";
        Long authorId = 1L;
        Long genreId = 2L;
        Long id = 1L;

        Author author = new Author("a1");
        author.setId(authorId);

        Genre genre = new Genre("g1");
        genre.setId(genreId);

        Book book = new Book();
        book.setName(str);
        book.setId(id);
        book.setAuthor(author);
        book.setGenre(genre);

        given(bookService.create((BookCreateDto) any())).willReturn(new BookDto(book.getId(),book.getName(), book.getAuthor().getName(), book.getGenre().getName()));
        given(authorService.read(authorId)).willReturn(author);
        given(genreService.read(genreId)).willReturn(genre);


        try {
            mvc.perform(post("/api/v1/book")
                            .content(mapper.writeValueAsBytes(new BookCreateDto(str, authorId, genreId)))
                            .contentType("application/json;charset=UTF-8"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.name").value(str))
                    .andExpect(jsonPath("$.authorName").value(author.getName()))
                    .andExpect(jsonPath("$.genreName").value(genre.getName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("Read")
    @Test
    public void testReadBook(){
        String str = "bookName";
        Long authorId = 1L;
        Long genreId = 2L;
        Long id = 1L;

        Author author = new Author("a1");
        author.setId(authorId);

        Genre genre = new Genre("g1");
        genre.setId(genreId);

        Book book = new Book();
        book.setName(str);
        book.setId(id);
        book.setAuthor(author);
        book.setGenre(genre);

        given(bookService.getById(id)).willReturn(
                new BookDto(book.getId(),book.getName(), book.getAuthor().getName(), book.getGenre().getName()));


        try {
            mvc.perform(get("/api/v1/book/"+id))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.name").value(str))
                    .andExpect(jsonPath("$.authorName").value(author.getName()))
                    .andExpect(jsonPath("$.genreName").value(genre.getName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("Update")
    @Test
    public void testUpdateBook(){
        String str = "bookName";
        Long authorId = 1L;
        Long genreId = 2L;
        Long id = 1L;

        Author author = new Author("a1");
        author.setId(authorId);

        Genre genre = new Genre("g1");
        genre.setId(genreId);

        Book book = new Book();
        book.setName(str);
        book.setId(id);
        book.setAuthor(author);
        book.setGenre(genre);


        given(bookService.getById(id)).willReturn(
                new BookDto(book.getId(),book.getName(), book.getAuthor().getName(), book.getGenre().getName())
        );
        when(bookService.update((BookUpdateDto) any()))
                .thenAnswer(i ->
                        new BookDto(book.getId(),book.getName(), book.getAuthor().getName(), book.getGenre().getName()));
        given(authorService.read(authorId)).willReturn(author);
        given(genreService.read(genreId)).willReturn(genre);


        try {
            mvc.perform(put("/api/v1/book")
                            .content(mapper.writeValueAsBytes(new BookUpdateDto(id, str, authorId, genreId)))
                            .contentType("application/json;charset=UTF-8"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.name").value(str))
                    .andExpect(jsonPath("$.authorName").value(author.getName()))
                    .andExpect(jsonPath("$.genreName").value(genre.getName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("Delete")
    @Test
    public void testDeleteBook(){
        String name = "bookName";
        Long authorId = 1L;
        Long genreId = 2L;
        Long id = 1L;

        Author author = new Author("a1");
        author.setId(authorId);

        Genre genre = new Genre("g1");
        genre.setId(genreId);

        Book book = new Book();
        book.setName(name);
        book.setId(id);
        book.setAuthor(author);
        book.setGenre(genre);


        try {
            mvc.perform(delete("/api/v1/book").content(mapper.writeValueAsBytes(new BookDeleteDto((id))))
                            .contentType("application/json;charset=UTF-8"))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
