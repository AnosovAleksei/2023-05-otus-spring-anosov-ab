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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentaryControllerTest {

    @MockBean
    CommentaryRepository commentaryRepository;

    @MockBean
    BookRepository bookRepository;

    @LocalServerPort
    private int port;



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


        var client = WebClient.create(String.format("http://localhost:%d", port));

        var result = client
                .get().uri("/api/v1/commentary")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Commentary.class)
                .collectList()
                .block();

        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getMessage()).isEqualTo(commentaryList.get(0).getMessage());

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

        var client = WebClient.create(String.format("http://localhost:%d", port));


        var result = client
                .post().uri("/api/v1/commentary")
                .body(BodyInserters.fromValue(new CommentaryCreateDto(book.getId(), str)))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Commentary.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertThat(result.getMessage()).isEqualTo(commentary.getMessage());
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

        var client = WebClient.create(String.format("http://localhost:%d", port));


        var result = client
                .get().uri("/api/v1/commentary/"+id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Commentary.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertThat(result.getMessage()).isEqualTo(commentary.getMessage());

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

        var client = WebClient.create(String.format("http://localhost:%d", port));


        var result = client
                .put().uri("/api/v1/commentary/"+id)
                .body(BodyInserters.fromValue(new CommentaryUpdateRequestDto(book.getId(), str)))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Commentary.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertThat(result.getMessage()).isEqualTo(commentary.getMessage());


    }

}
