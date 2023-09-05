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
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Commentary;
import ru.otus.domain.Genre;
import ru.otus.dto.CommentaryCreateDto;
import ru.otus.dto.CommentaryUpdateRequestDto;
import ru.otus.repository.BookRepository;
import ru.otus.repository.CommentaryRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@DisplayName("Проверка работы CommentaryController")
@WebFluxTest(CommentaryController.class)
public class CommentaryControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    CommentaryRepository commentaryRepository;

    @MockBean
    BookRepository bookRepository;





    @DisplayName("getAll")
    @Test
    public void testGetComments(){
        Author author = new Author("1L", "a1");
        Genre genre = new Genre("1L", "g1");
        Book book = new Book("2L", "xxx", author, genre );

        List<Commentary> commentaryList = new ArrayList<>(){{
            Commentary commentary1 = new Commentary();
            commentary1.setId("1L");
            commentary1.setBook(book);
            commentary1.setMessage("xxxx");

            add(commentary1);

            Commentary commentary2 = new Commentary();
            commentary2.setId("2L");
            commentary2.setBook(book);
            commentary2.setMessage("yyyy");
            add(commentary2);
        }};


        given(commentaryRepository.findAll()).willReturn(Flux.fromIterable(commentaryList));

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .get().uri("/api/v1/commentary")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$").isArray()//., Matchers.hasSize(2))
                .jsonPath("$[0].id").isEqualTo("1L")
                .jsonPath("$[0].message").isEqualTo("xxxx")
                .jsonPath("$[1].id").isEqualTo("2L")
                .jsonPath("$[1].message").isEqualTo("yyyy");;

    }

    @DisplayName("Create")
    @Test
    public void testCreateCommentary(){
        String str = "xxxx";
        String id = "1L";
        Author author = new Author("1L", "a1");
        Genre genre = new Genre("1L", "g1");
        Book book = new Book("2L", "xxx", author, genre );

        Commentary commentary = new Commentary();
        commentary.setId(id);
        commentary.setBook(book);
        commentary.setMessage(str);

        given(bookRepository.findById(anyString())).willReturn(Mono.just(book));
        given(commentaryRepository.save(any(Commentary.class))).willReturn(Mono.just(commentary));

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .post().uri("/api/v1/commentary")
                .body(BodyInserters.fromValue(new CommentaryCreateDto(book.getId(), str)))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.message").isEqualTo(str);
    }

    @DisplayName("Read")
    @Test
    public void testReadCommentary(){
        String str = "xxxx";
        String id = "1L";
        Author author = new Author("1L", "a1");
        Genre genre = new Genre("1L", "g1");
        Book book = new Book("2L", "xxx", author, genre );

        Commentary commentary = new Commentary();
        commentary.setId(id);
        commentary.setBook(book);
        commentary.setMessage(str);


        given(commentaryRepository.findById(anyString())).willReturn(Mono.just(commentary));

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .get().uri("/api/v1/commentary/"+id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.message").isEqualTo(str);


    }

    @DisplayName("Update")
    @Test
    public void testUpdateCommentary(){
        String str = "xxxx";
        String id = "1L";
        Author author = new Author("1L", "a1");
        Genre genre = new Genre("1L", "g1");
        Book book = new Book("2L", "xxx", author, genre );

        Commentary commentary = new Commentary();
        commentary.setId(id);
        commentary.setBook(book);
        commentary.setMessage(str);


        given(bookRepository.findById(anyString())).willReturn(Mono.just(book));

        given(commentaryRepository.findById(anyString())).willReturn(Mono.just(commentary));
        given(commentaryRepository.save(any(Commentary.class))).willReturn(Mono.just(commentary));

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .put().uri("/api/v1/commentary/"+id)
                .body(BodyInserters.fromValue(new CommentaryUpdateRequestDto(book.getId(), str)))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.message").isEqualTo(str);


    }

}
