package ru.otus.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.dto.StudentRequestDto;
import ru.otus.dto.StudentResponceDto;
import ru.otus.service.StudentAttendanceService;
import ru.otus.service.StudentService;
import ru.otus.service.UserAccessService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@DisplayName("Проверка работы StudentController")
@SpringBootTest(properties = {"spring.liquibase.enabled=false"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentControllerTest {
    private MockMvc mvc;


    @Autowired
    private WebApplicationContext context;


    @Autowired
    private ObjectMapper mapper;
    @MockBean
    StudentAttendanceService studentAttendanceService;

    @MockBean
    UserAccessService userAccessService;

    @MockBean
    StudentService studentService;


    @DisplayName("check not authorities")
    @WithMockUser( username= "user", roles={"NOT ROLR"})
    @Test
    public void checkNotAuthorities(){
        try {
            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();

            mvc.perform(get("/api/v1/team"))
                    .andExpect(status().isForbidden());
            mvc.perform(get("/api/v1/1/student"))
                    .andExpect(status().isForbidden());
            mvc.perform(get("/api/v1/1/student/1"))
                    .andExpect(status().isForbidden());
            mvc.perform(post("/api/v1/1/student").with(csrf()))
                    .andExpect(status().isForbidden());
            mvc.perform(get("/api/v1/1/control-date"))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("check not access for role TEACHER")
    @WithMockUser( username= "user", roles={"TEACHER"})
    @Test
    public void checkTeacherNotAccessAuthorities(){
        try {
            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();

            mvc.perform(get("/api/v1/1/student"))
                    .andExpect(status().isForbidden());
            mvc.perform(post("/api/v1/1/student").with(csrf()))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("check not access for role CAPTAIN")
    @WithMockUser( username= "user", roles={"CAPTAIN"})
    @Test
    public void checkCapitanNotAccessAuthorities(){
        try {
            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();

            mvc.perform(get("/api/v1/1/student/1"))
                    .andExpect(status().isForbidden());
            mvc.perform(get("/api/v1/1/control-date"))
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("check access for role CAPTAIN get /api/v1/1/student")
    @WithMockUser( username= "user", roles={"CAPTAIN"})
    @Test
    public void checkCapitanAuthorities1(){

        List<StudentResponceDto> studentResponceDtoList = new ArrayList<>(){{
            add(new StudentResponceDto(1L, "s1", "n1", "p1", false));
            add(new StudentResponceDto(2L, "s2", "n2", "p2", false));
        }};
        given(studentService.getStudentByTeamId(anyLong())).willReturn(studentResponceDtoList);

        try {
            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();

            mvc.perform(get("/api/v1/1/student"))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @DisplayName("check access for role CAPTAIN post /api/v1/1/student")
    @WithMockUser( username= "user", roles={"CAPTAIN"})
    @Test
    public void checkCapitanAuthorities2(){

        List<StudentRequestDto> studentRequestDtoList = new ArrayList<>(){{
            add(new StudentRequestDto(1L, false));
            add(new StudentRequestDto(2L, false));
        }};


        doNothing().when(studentAttendanceService).setStudentAttendanceControl(anyList());

        try {
            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();

            mvc.perform(post("/api/v1/1/student")
                    .content(mapper.writeValueAsBytes(studentRequestDtoList))
                    .with(csrf())
                    .contentType("application/json;charset=UTF-8"))
                    .andExpect(status().isCreated());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("check access for role CAPTAIN get /api/v1/team")
    @WithMockUser( username= "user", roles={"CAPTAIN"})
    @Test
    public void checkCapitanAuthorities3(){
        given(userAccessService.getTeamsByUserName(anyString())).willReturn(new ArrayList<>());



        try {
            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();

            mvc.perform(get("/api/v1/team"))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @DisplayName("check access for role TEACHER get /api/v1/1/control-date")
    @WithMockUser( username= "user", roles={"TEACHER"})
    @Test
    public void checkTeacherAuthorities1(){

        given(studentAttendanceService.getControlDateList(anyLong())).willReturn(new ArrayList<>());


        try {
            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();

            mvc.perform(get("/api/v1/1/control-date"))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("check access for role TEACHER get /api/v1/1/student/{controlDate}")
    @WithMockUser( username= "user", roles={"TEACHER"})
    @Test
    public void checkTeacherAuthorities2(){

        given(studentAttendanceService.getStudentByTeamIdForDate(anyLong(), anyString())).willReturn(new ArrayList<>());


        try {
            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();

            mvc.perform(get("/api/v1/1/student/2023-10-20"))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("check access for role CAPTAIN get /api/v1/team")
    @WithMockUser( username= "user", roles={"TEACHER"})
    @Test
    public void checkTeacherAuthorities3(){
        given(userAccessService.getTeamsByUserName(anyString())).willReturn(new ArrayList<>());

        try {
            mvc = MockMvcBuilders
                    .webAppContextSetup(context)
                    .apply(springSecurity())
                    .build();

            mvc.perform(get("/api/v1/team"))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
