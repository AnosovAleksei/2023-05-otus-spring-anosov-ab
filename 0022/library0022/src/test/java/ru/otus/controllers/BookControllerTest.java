package ru.otus.controllers;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.dto.BookCreateDto;
import ru.otus.dto.BookDto;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookUpdateRequestDto;
import ru.otus.repository.AuthorRepository;
import ru.otus.repository.BookRepository;
import ru.otus.repository.GenreRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;



@DisplayName("Проверка работы BookController")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @MockBean
    BookRepository bookRepository;
    @MockBean
    AuthorRepository authorRepository;

    @MockBean
    GenreRepository genreRepository;

    @LocalServerPort
    private int port;



    @DisplayName("getAll")
    @Test
    public void testGetBooks(){
        Author author = new Author("1L", "a1");
        Genre genre = new Genre("1L", "g1");

        List<Book> bookList = new ArrayList<>(){{


            Book book = new Book();
            book.setId("1L");
            book.setAuthor(author);
            book.setGenre(genre);
            book.setName("zzzzz1");
            add(book);

        }};
        List<BookDto> bookDtoList = new ArrayList<>(){{
            for(Book book: bookList){
                add(new BookDto(book.getId(), book.getName(), book.getAuthor().getName(), book.getGenre().getName()));
            }
        }};


        given(bookRepository.findAll()).willReturn(Flux.fromIterable(bookList));


        var client = WebClient.create(String.format("http://localhost:%d", port));

        var result = client
                .get().uri("/api/v1/book")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Book.class)
                .collectList()
                .block();

        //then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(bookDtoList.get(0).getName());

    }

    @DisplayName("Create")
    @Test
    public void testCreateBook(){
        String str = "bookName";
        String id = "1L";

        Author author = new Author("1L", "a1");
        Genre genre = new Genre("1L", "g1");

        Book book = new Book();
        book.setName(str);
        book.setId(id);
        book.setAuthor(author);
        book.setGenre(genre);

        given(authorRepository.findById(anyString())).willReturn(Mono.just(author));
        given(genreRepository.findById(anyString())).willReturn(Mono.just(genre));
        given(bookRepository.save(any(Book.class))).willReturn(Mono.just(book));

        var client = WebClient.create(String.format("http://localhost:%d", port));


        var result = client
                .post().uri("/api/v1/book")
                .body(BodyInserters.fromValue(new BookCreateDto(str, author.getId(), genre.getId())))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Book.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertThat(result.getName()).isEqualTo(book.getName());

    }

    @DisplayName("Read")
    @Test
    public void testReadBook(){
        String str = "bookName";
        String id = "1L";

        Author author = new Author("1L", "a1");
        Genre genre = new Genre("1L", "g1");

        Book book = new Book();
        book.setName(str);
        book.setId(id);
        book.setAuthor(author);
        book.setGenre(genre);

        given(bookRepository.findById(anyString())).willReturn(Mono.just(book));

        var client = WebClient.create(String.format("http://localhost:%d", port));


        var result = client
                .get().uri("/api/v1/book/"+id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Book.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertThat(result.getName()).isEqualTo(book.getName());

    }

    @DisplayName("Update")
    @Test
    public void testUpdateBook(){
        String str = "bookName";
        String id = "1L";

        Author author = new Author("1L", "a1");
        Genre genre = new Genre("1L", "g1");

        Book book = new Book();
        book.setName(str);
        book.setId(id);
        book.setAuthor(author);
        book.setGenre(genre);


        given(authorRepository.findById(anyString())).willReturn(Mono.just(author));
        given(genreRepository.findById(anyString())).willReturn(Mono.just(genre));
        given(bookRepository.findById(anyString())).willReturn(Mono.just(book));


        given(bookRepository.save(any(Book.class))).willReturn(Mono.just(book));

        var client = WebClient.create(String.format("http://localhost:%d", port));


        var result = client
                .put().uri("/api/v1/book/"+id)
                .body(BodyInserters.fromValue(new BookUpdateRequestDto(str, author.getId(), genre.getId())))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Book.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertThat(result.getName()).isEqualTo(book.getName());

    }

    @DisplayName("Delete")
    @Test
    public void testDeleteBook(){
        String str = "bookName";
        String id = "1L";

        Author author = new Author("1L", "a1");
        Genre genre = new Genre("1L", "g1");

        Book book = new Book();
        book.setName(str);
        book.setId(id);
        book.setAuthor(author);
        book.setGenre(genre);


        given(bookRepository.findById(anyString())).willReturn(Mono.just(book));

        var client = WebClient.create(String.format("http://localhost:%d", port));


        var result = client
                .delete().uri("/api/v1/book/"+id)
                .exchange()
                .timeout(Duration.ofSeconds(3))
                .block().statusCode().is2xxSuccessful();
    }

}
