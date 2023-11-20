package ru.otus.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dto.TeamDto;
import ru.otus.service.UserAccessService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TeamController {


    private final UserAccessService userAccessService;


    @GetMapping("/api/v1/team")
    public List<TeamDto> getStudents(@AuthenticationPrincipal User user) {
        return userAccessService.getTeamsByUserName(user.getUsername());
    }

}
