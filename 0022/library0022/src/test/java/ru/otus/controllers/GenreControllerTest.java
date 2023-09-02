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
import ru.otus.domain.Genre;
import ru.otus.dto.GenreCreateDto;
import ru.otus.dto.GenreUpdateRequestDto;
import ru.otus.repository.GenreRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@DisplayName("Проверка работы GenreController")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GenreControllerTest {

    @MockBean
    GenreRepository genreRepository;

    @LocalServerPort
    private int port;



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


        var client = WebClient.create(String.format("http://localhost:%d", port));

        var result = client
                .get().uri("/api/v1/genre")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Genre.class)
                .collectList()
                .block();

        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo(genreList.get(0).getName());

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

        var client = WebClient.create(String.format("http://localhost:%d", port));


        var result = client
                .post().uri("/api/v1/genre").body(BodyInserters.fromValue(new GenreCreateDto(str)))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Author.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertThat(result.getName()).isEqualTo(genre.getName());

    }

    @DisplayName("Read")
    @Test
    public void testReadGenre(){
        String str = "zzzz";
        String id = "2L";
        Genre genre = new Genre(str);
        genre.setId(id);


        given(genreRepository.findById(anyString())).willReturn(Mono.just(genre));

        var client = WebClient.create(String.format("http://localhost:%d", port));


        var result = client
                .get().uri("/api/v1/genre/"+id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Author.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertThat(result.getName()).isEqualTo(genre.getName());

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

        var client = WebClient.create(String.format("http://localhost:%d", port));


        var result = client
                .put().uri("/api/v1/genre/"+id).body(BodyInserters.fromValue(new GenreUpdateRequestDto((str))))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Author.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertThat(result.getName()).isEqualTo(genre.getName());

    }

}
