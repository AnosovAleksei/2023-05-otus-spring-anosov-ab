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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.controllers.CommentaryController;
import ru.otus.domain.Commentary;
import ru.otus.dto.CommentaryCreateDto;
import ru.otus.dto.CommentaryUpdateDto;
import ru.otus.dto.CommentaryUpdateRequestDto;
import ru.otus.dto.GenreCreateDto;
import ru.otus.service.CommentaryService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Проверка работы CommentaryController")
@WebMvcTest(CommentaryController.class)
public class CommentaryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;
    @MockBean
    CommentaryService commentaryService;


    @DisplayName("check not authorities")
    @Test
    public void checkNotAuthorities(){
        try {
            mvc.perform(get("/api/v1/commentary"))
                    .andExpect(status().isUnauthorized());
            mvc.perform(get("/api/v1/commentary/1"))
                    .andExpect(status().isUnauthorized());
            mvc.perform(put("/api/v1/commentary/1").with(csrf()))
                    .andExpect(status().isUnauthorized());
            mvc.perform(post("/api/v1/commentary").with(csrf()))
                    .andExpect(status().isUnauthorized());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("getAll")
    @WithMockUser( username= "user", authorities = {"ROLE_ADMIN"})
    @Test
    public void testGetComments(){
        List<Commentary> commentaryList = new ArrayList<>(){{
            Commentary commentary1 = new Commentary();
            commentary1.setId(1L);
            commentary1.setBookId(2L);
            commentary1.setMessage("xxxx");

            add(commentary1);

            Commentary commentary2 = new Commentary();
            commentary2.setId(2L);
            commentary2.setBookId(2L);
            commentary2.setMessage("yyyy");
            add(commentary2);
        }};


        given(commentaryService.getAll()).willReturn(commentaryList);

        try {
            Object l1 = mvc.perform(get("/api/v1/commentary"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", Matchers.hasSize(2)))
                    .andExpect(jsonPath("$[0].id").value(1L))
                    .andExpect(jsonPath("$[0].message").value("xxxx"))
                    .andExpect(jsonPath("$[0].bookId").value(2L))

                    .andExpect(jsonPath("$[1].id").value(2L))
                    .andExpect(jsonPath("$[1].message").value("yyyy"))
                    .andExpect(jsonPath("$[1].bookId").value(2L));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("Create")
    @WithMockUser( username= "user", authorities = {"ROLE_ADMIN"})
    @Test
    public void testCreateCommentary(){
        String str = "xxxx";
        Long bookId = 2L;
        Long id = 1L;
        Commentary commentary = new Commentary();
        commentary.setId(id);
        commentary.setBookId(bookId);
        commentary.setMessage(str);

        given(commentaryService.create((CommentaryCreateDto)any())).willReturn(commentary);

        try {
            mvc.perform(post("/api/v1/commentary").content(mapper.writeValueAsBytes(new CommentaryCreateDto(bookId, str)))
                            .with(csrf())
                            .contentType("application/json;charset=UTF-8"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.bookId").value(bookId))
                    .andExpect(jsonPath("$.message").value(str));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Read")
    @WithMockUser( username= "user", authorities = {"ROLE_ADMIN"})
    @Test
    public void testReadCommentary(){
        String str = "xxxx";
        Long bookId = 2L;
        Long id = 1L;
        Commentary commentary = new Commentary();
        commentary.setId(id);
        commentary.setBookId(bookId);
        commentary.setMessage(str);


        given(commentaryService.read(anyLong())).willReturn(commentary);


        try {
            Object l1 = mvc.perform(get("/api/v1/commentary/"+id))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.bookId").value(bookId))
                    .andExpect(jsonPath("$.message").value(str));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("Update")
    @WithMockUser( username= "user", authorities = {"ROLE_ADMIN"})
    @Test
    public void testUpdateCommentary(){
        String str = "xxxx";
        Long bookId = 2L;
        Long id = 1L;
        Commentary commentary = new Commentary();
        commentary.setId(id);
        commentary.setBookId(bookId);
        commentary.setMessage(str);;


        when(commentaryService.update((CommentaryUpdateDto)any())).thenAnswer(i -> commentary);

        try {
            Object l1 = mvc.perform(put("/api/v1/commentary/"+id)
                            .content(mapper.writeValueAsBytes(new CommentaryUpdateRequestDto(bookId, str)))
                            .with(csrf())
                            .contentType("application/json;charset=UTF-8"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.bookId").value(bookId))
                    .andExpect(jsonPath("$.message").value(str));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
