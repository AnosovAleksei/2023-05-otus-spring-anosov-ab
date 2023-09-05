package ru.otus.controllers;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
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
@WebFluxTest(BookController.class)
public class BookControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    BookRepository bookRepository;
    @MockBean
    AuthorRepository authorRepository;

    @MockBean
    GenreRepository genreRepository;



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

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .get().uri("/api/v1/book")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$").isArray()//., Matchers.hasSize(2))
                .jsonPath("$[0].id").isEqualTo("1L")
                .jsonPath("$[0].name").isEqualTo("zzzzz1");


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

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .post().uri("/api/v1/book")
                .body(BodyInserters.fromValue(new BookCreateDto(str, author.getId(), genre.getId())))
                .accept(MediaType.APPLICATION_JSON)

                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.name").isEqualTo(str);

    }

    @DisplayName("Create error")
    @Test
    public void testCreateBookError(){
        String str = "bookName";
        String id = "1L";

        Author author = new Author("1L", "a1");
        Genre genre = new Genre("1L", "g1");

        Book book = new Book();
        book.setName(str);
        book.setId(id);
        book.setAuthor(author);
        book.setGenre(genre);

        given(bookRepository.save(any(Book.class))).willReturn(Mono.just(book));

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .post().uri("/api/v1/book")
                .body(BodyInserters.fromValue(new BookCreateDto(str, author.getId(), genre.getId())))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();

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

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .get().uri("/api/v1/book/"+id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.name").isEqualTo(str);

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

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .put().uri("/api/v1/book/"+id)
                .body(BodyInserters.fromValue(new BookUpdateRequestDto(str, author.getId(), genre.getId())))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.name").isEqualTo(str);

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
        given(bookRepository.delete(any(Book.class))).willReturn(Mono.when());


//        var webTestClientForTest = webTestClient. mutate()
//                .responseTimeout(Duration.ofSeconds(20))
//                .build();

        webTestClient
                .delete().uri("/api/v1/book/"+id)
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

}
