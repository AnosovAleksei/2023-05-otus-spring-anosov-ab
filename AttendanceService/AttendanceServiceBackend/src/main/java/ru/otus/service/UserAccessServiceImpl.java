package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.NotFoundException;
import ru.otus.domain.UserAccess;
import ru.otus.dto.TeamDto;
import ru.otus.repository.UserAccessRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserAccessServiceImpl implements UserAccessService {

    private final UserAccessRepository userRepository;


    @Override
    public List<UserAccess> getAllUsers() {
        return userRepository.findAll();
    }



    @Override
    public List<TeamDto> getTeamsByUserName(String username) {
        UserAccess userAccess = userRepository.findAllByUsername(username);
        if(userAccess == null){
            throw new NotFoundException("username with name :" + username + " does not exist");
        }
        return userAccess.getTeam().stream().map(m->new TeamDto(m.getId(), m.getDescription())).toList();
    }


}
