package ru.otus.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.domain.NotFoundException;
import ru.otus.domain.Team;
import ru.otus.domain.UserAccess;
import ru.otus.dto.TeamDto;
import ru.otus.repository.UserAccessRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@DisplayName("Проверка работы UserAccessService")
@SpringBootTest(properties = {"spring.liquibase.enabled=false"})
public class UserAccessServiceTest {

    @MockBean
    UserAccessRepository userRepository;;

    @Autowired
    UserAccessService userAccessService;

    @DisplayName("check null")
    @Test
    public void getTeamsByUserName1(){

        given(userRepository.findAllByUsername(anyString())).willReturn(null);

        Assertions.assertThrows(NotFoundException.class, () ->
                userAccessService.getTeamsByUserName("xxxx"));
    }

    @DisplayName("check empty")
    @Test
    public void getTeamsByUserName2(){
        List<Team> teamList = new ArrayList<>();

        given(userRepository.findAllByUsername(anyString()))
                .willReturn(new UserAccess(1L, teamList, "zzz", "xxxx", "ffff"));

        List<TeamDto> teamDtos = userAccessService.getTeamsByUserName("xxxx");

        Assertions.assertEquals(teamDtos.size(), 0);
    }

    @DisplayName("check work")
    @Test
    public void getTeamsByUserName3(){
        List<Team> teamList = List.of(
            new Team(1L, "xxx")
        );

        given(userRepository.findAllByUsername(anyString()))
                .willReturn(new UserAccess(1L, teamList, "zzz", "xxxx", "ffff"));

        List<TeamDto> teamDtos = userAccessService.getTeamsByUserName("xxxx");

        Assertions.assertEquals(teamDtos.size(), 1);
    }


}
