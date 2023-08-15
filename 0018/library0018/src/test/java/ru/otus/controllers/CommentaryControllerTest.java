package ru.otus.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.controllers.commentaryController.CommentaryController;
import ru.otus.domain.Commentary;
import ru.otus.service.CommentaryService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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


    @DisplayName("getAll")
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
            Object l1 = mvc.perform(get("/comments"))
                    .andExpect(status().isOk())
                    .andReturn().getModelAndView().getModel().get("comments");
            List<Commentary> l2 = (List<Commentary>)l1;
            Assertions.assertEquals(l2.size(),2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("Create")
    @Test
    public void testCreateCommentary(){
        String str = "xxxx";
        Long bookId = 2L;
        Long id = 1L;
        Commentary commentary = new Commentary();
        commentary.setId(id);
        commentary.setBookId(bookId);
        commentary.setMessage(str);

        given(commentaryService.create(any())).willReturn(commentary);

        try {
            Object l1 = mvc.perform(post("/commentary").content("message="+str+"&bookId="+bookId)
                            .contentType("application/x-www-form-urlencoded;charset=UTF-8"))
                    .andExpect(status().isOk())
                    .andReturn().getModelAndView().getModel().get("commentary");
            Commentary l2 = (Commentary)l1;
            Assertions.assertEquals(l2.getMessage(),str);
            Assertions.assertEquals(l2.getId(),id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Read")
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
            Object l1 = mvc.perform(get("/commentary").param("id", String.valueOf(id)))
                    .andExpect(status().isOk())
                    .andReturn().getModelAndView().getModel().get("commentary");
            Commentary l2 = (Commentary)l1;
            Assertions.assertEquals(l2.getMessage(), str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @DisplayName("Update")
    @Test
    public void testUpdateCommentary(){
        String str = "xxxx";
        Long bookId = 2L;
        Long id = 1L;
        Commentary commentary = new Commentary();
        commentary.setId(id);
        commentary.setBookId(bookId);
        commentary.setMessage(str);;


        when(commentaryService.update(any())).thenAnswer(i -> i.getArguments()[0]);

        try {
            Object l1 = mvc.perform(put("/commentary").content("message="+str+"&id="+id+"&bookId="+bookId)
                            .contentType("application/x-www-form-urlencoded;charset=UTF-8"))
                    .andExpect(status().isOk())
                    .andReturn().getModelAndView().getModel().get("commentary");
            Commentary l2 = (Commentary)l1;
            Assertions.assertEquals(l2.getId(),id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
