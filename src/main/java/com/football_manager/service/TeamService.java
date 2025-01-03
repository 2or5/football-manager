package com.football_manager.service;

import com.football_manager.dto.TeamDto;
import com.football_manager.dto.request.TeamDtoRequest;
import com.football_manager.entity.Team;
import com.football_manager.exception.IdNotFoundException;
import com.football_manager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamDto> getAllTeams() {
        List<Team> teams = teamRepository.getAllTeams();

        return teams.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TeamDto getTeamById(Integer id) {
        Team team = teamRepository.getTeamById(id)
                .orElseThrow(() -> new IdNotFoundException("The team does not exist by this id: " + id));

        return mapToDto(team);
    }

    public TeamDto createTeam(TeamDtoRequest teamDtoRequest) {
        Team team = Team.builder()
                .name(teamDtoRequest.getName())
                .balance(teamDtoRequest.getBalance())
                .commissionPercentage(teamDtoRequest.getCommissionPercentage())
                .build();

        Team createdTeam = teamRepository.saveTeam(team);
        return mapToDto(createdTeam);
    }

    public TeamDto updateTeam(Integer id, TeamDtoRequest teamDtoRequest) {
        Team team = teamRepository.getTeamById(id)
                .orElseThrow(() -> new IdNotFoundException("The team does not exist by this id: " + id));

        Team teamToBeUpdated = team.toBuilder()
                .name(teamDtoRequest.getName())
                .balance(teamDtoRequest.getBalance())
                .commissionPercentage(teamDtoRequest.getCommissionPercentage())
                .build();

        Team updatedTeam = teamRepository.updateTeam(teamToBeUpdated);
        return mapToDto(updatedTeam);
    }

    public String deleteTeam(Integer id) {
        Integer deletedTeam = teamRepository.deleteTeam(id);
        if (deletedTeam == 0) {
            throw new IdNotFoundException("The team does not exist by this id: " + id);
        } else {
            return "Team deleted successfully";
        }
    }

    private TeamDto mapToDto(Team team) {
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .balance(team.getBalance())
                .commissionPercentage(team.getCommissionPercentage())
                .build();
    }
}
