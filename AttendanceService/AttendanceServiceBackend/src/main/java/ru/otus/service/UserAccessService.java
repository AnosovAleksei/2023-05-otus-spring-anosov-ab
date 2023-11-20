package ru.otus.service;

import ru.otus.domain.UserAccess;
import ru.otus.dto.TeamDto;

import java.util.List;

public interface UserAccessService {

    List<UserAccess> getAllUsers();

    List<TeamDto> getTeamsByUserName(String username);

}
