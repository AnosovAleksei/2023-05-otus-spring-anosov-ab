package ru.otus.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.domain.Author;
import ru.otus.dto.AuthorCreateDto;
import ru.otus.dto.AuthorUpdateDto;
import ru.otus.dto.AuthorUpdateRequestDto;
import ru.otus.service.AuthorService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.hamcrest.Matchers;

@DisplayName("Проверка работы AuthorController")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorControllerTest {

//    @Autowired
    private MockMvc mvc;


    @Autowired
    private WebApplicationContext context;


    @Autowired
    private ObjectMapper mapper;
    @MockBean
    AuthorService authorService;


    @DisplayName("check not authorities")
    @WithMockUser( username= "user", roles={"USER"})
    @Test
    public void checkNotAuthorities(){
        try {
            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();


            mvc.perform(get("/api/v1/author"))
                    .andExpect(status().isOk());
            mvc.perform(get("/api/v1/author/1"))
                    .andExpect(status().isOk());
            mvc.perform(put("/api/v1/author/1").with(csrf()))
                    .andExpect(status().isForbidden());
            mvc.perform(post("/api/v1/author").with(csrf()))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("check not authorities2")
    @WithMockUser( username= "user", roles={"NOT ROLR"})
    @Test
    public void checkNotAuthorities2(){
        try {
            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();


            mvc.perform(get("/api/v1/author"))
                    .andExpect(status().isForbidden());
            mvc.perform(get("/api/v1/author/1"))
                    .andExpect(status().isForbidden());
            mvc.perform(put("/api/v1/author/1").with(csrf()))
                    .andExpect(status().isForbidden());
            mvc.perform(post("/api/v1/author").with(csrf()))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @DisplayName("getAll")
    @WithMockUser( username= "user", roles={"ADMIN"})
    @Test
    public void testGetAuthors(){
        List<Author> authorList = new ArrayList<>(){{
            Author author1 = new Author("a1");
            author1.setId(1L);
            add(author1);

            Author author2 = new Author("a2");
            author2.setId(2L);
            add(author2);
        }};


        given(authorService.getAll()).willReturn(authorList);


        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();



        try {
            Object l1 = mvc.perform(get("/api/v1/author"))
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
    @WithMockUser( username= "user", roles={"ADMIN"})
    @Test
    public void testCreateAuthor(){
        String str = "zzzz";
        Long id = 2L;
        Author author = new Author(str);
        author.setId(id);

        given(authorService.create((AuthorCreateDto)any())).willReturn(author);
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        try {
            mvc.perform(post("/api/v1/author").content(mapper.writeValueAsBytes(new AuthorCreateDto(str)))
                            .with(csrf())
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
    @WithMockUser( username= "user", roles={"ADMIN"})
    @Test
    public void testReadAuthors(){
        String str = "zzzz";
        Long id = 2L;
        Author author = new Author(str);
        author.setId(id);

        Author authorTemp = new Author(str);

        given(authorService.read(anyLong())).willReturn(author);

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        try {
            mvc.perform(get("/api/v1/author/"+id))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.name").value(str));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @DisplayName("Update")
    @WithMockUser( username= "user", roles={"ADMIN"})
    @Test
    public void testUpdateAuthor(){
        String str = "zzzz";
        Long id = 2L;

        Author author = new Author(str);
        author.setId(id);

        when(authorService.update((AuthorUpdateDto)any())).thenAnswer(i -> author);
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        try {
            mvc.perform(put("/api/v1/author/"+id)
                            .content(mapper.writeValueAsBytes(new AuthorUpdateRequestDto(str)))
                            .with(csrf())
                            .contentType("application/json;charset=UTF-8"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id))
                    .andExpect(jsonPath("$.name").value(str));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
