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
import ru.otus.dto.AuthorCreateDto;
import ru.otus.dto.AuthorUpdateRequestDto;
import ru.otus.repository.AuthorRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Проверка работы AuthorController")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorControllerTest {


    @MockBean
    AuthorRepository authorRepository;

    @LocalServerPort
    private int port;

    @DisplayName("getAll")
    @Test
    public void testGetAuthors(){

        List<Author> authorList = new ArrayList<>(){{
            Author author1 = new Author("a1");
            author1.setId("1L");
            add(author1);

            Author author2 = new Author("a2");
            author2.setId("2L");
            add(author2);
        }};


        given(authorRepository.findAll()).willReturn(Flux.fromIterable(authorList));


        var client = WebClient.create(String.format("http://localhost:%d", port));

        var result = client
                .get().uri("/api/v1/author")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Author.class)
                .collectList()
                .block();

        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo(authorList.get(0).getName());
    }

    @DisplayName("Create")
    @Test
    public void testCreateAuthor(){
        String str = "zzzz";
        String id = "2L";
        Author author = new Author(str);
        author.setId(id);

        given(authorRepository.findByName(any())).willReturn(Mono.empty());
        given(authorRepository.save(any(Author.class))).willReturn(Mono.just(author));

        var client = WebClient.create(String.format("http://localhost:%d", port));


        var result = client
                .post().uri("/api/v1/author").body(BodyInserters.fromValue(new AuthorCreateDto(str)))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Author.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertThat(result.getName()).isEqualTo(author.getName());
    }

    @DisplayName("Read")
    @Test
    public void testReadAuthors(){
        String str = "zzzz";
        String id = "2L";
        Author author = new Author(str);
        author.setId(id);

        Author authorTemp = new Author(str);

        given(authorRepository.findById(anyString())).willReturn(Mono.just(author));

        var client = WebClient.create(String.format("http://localhost:%d", port));


        var result = client
                .get().uri("/api/v1/author/"+id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Author.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertThat(result.getName()).isEqualTo(author.getName());

    }

    @DisplayName("Update")
    @Test
    public void testUpdateAuthor(){
        String str = "zzzz";
        String id = "2L";

        Author author = new Author(str);
        author.setId(id);

        given(authorRepository.findById(anyString())).willReturn(Mono.just(author));
        given(authorRepository.save(any(Author.class))).willReturn(Mono.just(author));

        var client = WebClient.create(String.format("http://localhost:%d", port));


        var result = client
                .put().uri("/api/v1/author/"+id).body(BodyInserters.fromValue(new AuthorUpdateRequestDto((str))))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Author.class)
                .timeout(Duration.ofSeconds(3))
                .block();

        assertThat(result.getName()).isEqualTo(author.getName());

    }

}
