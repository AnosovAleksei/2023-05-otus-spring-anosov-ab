package ru.otus.controllers;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
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
import static org.mockito.BDDMockito.given;

import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.beans.factory.annotation.Autowired;


@DisplayName("Проверка работы AuthorController")
@WebFluxTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    AuthorRepository authorRepository;


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


        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();


        webTestClientForTest
                .get().uri("/api/v1/author")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$").isArray()//., Matchers.hasSize(2))
                .jsonPath("$[0].id").isEqualTo("1L")
                .jsonPath("$[0].name").isEqualTo("a1")
                .jsonPath("$[1].id").isEqualTo("2L")
                .jsonPath("$[1].name").isEqualTo("a2");;
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

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .post().uri("/api/v1/author").body(BodyInserters.fromValue(new AuthorCreateDto(str)))
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
    public void testReadAuthors(){
        String str = "zzzz";
        String id = "2L";
        Author author = new Author(str);
        author.setId(id);

        Author authorTemp = new Author(str);

        given(authorRepository.findById(anyString())).willReturn(Mono.just(author));

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .get().uri("/api/v1/author/"+id)
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
    public void testUpdateAuthor(){
        String str = "zzzz";
        String id = "2L";

        Author author = new Author(str);
        author.setId(id);

        given(authorRepository.findById(anyString())).willReturn(Mono.just(author));
        given(authorRepository.save(any(Author.class))).willReturn(Mono.just(author));

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .put().uri("/api/v1/author/"+id).body(BodyInserters.fromValue(new AuthorUpdateRequestDto((str))))
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
    public void testUpdateAuthorErr(){
        String str = "zzzz";
        String id = "2L";

        Author author = new Author(str);
        author.setId(id);

        given(authorRepository.findById(anyString())).willReturn(Mono.empty());
        given(authorRepository.save(any(Author.class))).willReturn(Mono.just(author));

        var webTestClientForTest = webTestClient.mutate()
                .responseTimeout(Duration.ofSeconds(20))
                .build();

        webTestClientForTest
                .put().uri("/api/v1/author/"+id).body(BodyInserters.fromValue(new AuthorUpdateRequestDto((str))))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();

    }
}
