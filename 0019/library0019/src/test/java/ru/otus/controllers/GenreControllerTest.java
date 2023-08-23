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
import ru.otus.controllers.GenreController;
import ru.otus.domain.Genre;
import ru.otus.dto.AuthorCreateDto;
import ru.otus.dto.AuthorUpdateDto;
import ru.otus.dto.GenreCreateDto;
import ru.otus.dto.GenreUpdateDto;
import ru.otus.service.AuthorService;
import ru.otus.service.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Проверка работы GenreController")
@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;
    @MockBean
    GenreService genreService;


    @DisplayName("Проверка работы getAll")
    @Test
    public void testGetGenries(){
        List<Genre> genreList = new ArrayList<>(){{
            Genre genre1 = new Genre("a1");
            genre1.setId(1L);
            add(genre1);

            Genre genre2 = new Genre("a2");
            genre2.setId(2L);
            add(genre2);
        }};


        given(genreService.getAll()).willReturn(genreList);

        try {
            Object l1 = mvc.perform(get("/api/v1/genre"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", Matchers.hasSize(2)))
                    .andExpect(jsonPath("$[0].id").value(1L))
                    .andExpect(jsonPath("$[0].name").value("a1"))
                    .andExpect(jsonPath("$[1].id").value(2L))
                    .andExpect(jsonPath("$[1].name").value("a2"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("Create")
    @Test
    public void testCreateGenre(){
        String str = "zzzz";
        Long id = 2L;
        Genre genre = new Genre(str);
        genre.setId(id);

        given(genreService.create((GenreCreateDto) any())).willReturn(genre);

        try {
            mvc.perform(post("/api/v1/genre").content(mapper.writeValueAsBytes(new GenreCreateDto(str)))
                    .contentType("application/json;charset=UTF-8"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.name").value(str));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("Read")
    @Test
    public void testReadGenre(){
        String str = "zzzz";
        Long id = 2L;
        Genre genre = new Genre(str);
        genre.setId(id);


        given(genreService.read(anyLong())).willReturn(genre);


        try {
            mvc.perform(get("/api/v1/genre/"+id))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.name").value(str));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("Update")
    @Test
    public void testUpdateGenre(){
        String str = "zzzz";
        Long id = 2L;

        Genre genre = new Genre(str);
        genre.setId(id);
        when(genreService.update((GenreUpdateDto) any())).thenAnswer(i -> genre);

        try {
            mvc.perform(put("/api/v1/genre").content(mapper.writeValueAsBytes(new GenreUpdateDto(id, str)))
                            .contentType("application/json;charset=UTF-8"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.name").value(str));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
