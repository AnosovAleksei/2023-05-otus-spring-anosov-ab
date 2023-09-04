package ru.otus.controllers;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorCreateDto;
import ru.otus.dto.AuthorUpdateRequestDto;
import ru.otus.repository.GenreRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@DisplayName("Проверка работы GenreController")
@WebFluxTest(GenreController.class)
public class GenreControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    GenreRepository genreRepository;





    @DisplayName("Проверка работы getAll")
    @Test
    public void testGetGenries(){
        List<Genre> genreList = new ArrayList<>(){{
            Genre genre1 = new Genre("a1");
            genre1.setId("1L");
            add(genre1);

            Genre genre2 = new Genre("a2");
            genre2.setId("2L");
            add(genre2);
        }};


        given(genreRepository.findAll()).willReturn(Flux.fromIterable(genreList));

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();


        webTestClientForTest
                .get().uri("/api/v1/genre")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$").isArray()//., Matchers.hasSize(2))
                .jsonPath("$[0].id").isEqualTo("1L")
                .jsonPath("$[0].name").isEqualTo("a1")
                .jsonPath("$[1].id").isEqualTo("2L")
                .jsonPath("$[1].name").isEqualTo("a2");
    }

    @DisplayName("Create")
    @Test
    public void testCreateGenre(){
        String str = "zzzz";
        String id = "2L";
        Genre genre = new Genre(str);
        genre.setId(id);

        given(genreRepository.findByName(any())).willReturn(Mono.empty());
        given(genreRepository.save(any(Genre.class))).willReturn(Mono.just(genre));

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .post().uri("/api/v1/genre").body(BodyInserters.fromValue(new AuthorCreateDto(str)))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo("2L")
                .jsonPath("$.name").isEqualTo("zzzz");


    }

    @DisplayName("Read")
    @Test
    public void testReadGenre(){
        String str = "zzzz";
        String id = "2L";
        Genre genre = new Genre(str);
        genre.setId(id);


        given(genreRepository.findById(anyString())).willReturn(Mono.just(genre));

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .get().uri("/api/v1/genre/"+id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.name").isEqualTo(str);

    }

    @DisplayName("Update")
    @Test
    public void testUpdateGenre(){
        String str = "zzzz";
        String id = "2L";

        Genre genre = new Genre(str);
        genre.setId(id);
        given(genreRepository.findById(anyString())).willReturn(Mono.just(genre));
        given(genreRepository.save(any(Genre.class))).willReturn(Mono.just(genre));

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .put().uri("/api/v1/genre/"+id).body(BodyInserters.fromValue(new AuthorUpdateRequestDto((str))))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.name").isEqualTo(str);

    }

    @DisplayName("Update exeption")
    @Test
    public void testUpdateGenreError(){
        String str = "zzzz";
        String id = "2L";

        Genre genre = new Genre(str);
        genre.setId(id);
        given(genreRepository.findById(anyString())).willReturn(Mono.empty());
        given(genreRepository.save(any(Genre.class))).willReturn(Mono.just(genre));

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .put().uri("/api/v1/genre/"+id).body(BodyInserters.fromValue(new AuthorUpdateRequestDto((str))))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();

    }

}
