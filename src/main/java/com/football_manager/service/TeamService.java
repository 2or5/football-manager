package com.football_manager.service;

import com.football_manager.dto.response.PlayerDtoResponse;
import com.football_manager.dto.response.TeamDtoResponse;
import com.football_manager.dto.request.TeamDtoRequest;
import com.football_manager.entity.Player;
import com.football_manager.entity.Team;
import com.football_manager.exception.IdNotFoundException;
import com.football_manager.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamService {

    private final String TEAM_NOT_FOUND_MESSAGE = "The team does not exist by this id: ";

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamDtoResponse> getAllTeams() {
        List<Team> teams = teamRepository.getAllTeams();

        return teams.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TeamDtoResponse getTeamById(Integer id) {
        Team team = teamRepository.getTeamById(id)
                .orElseThrow(() -> new IdNotFoundException(TEAM_NOT_FOUND_MESSAGE + id));

        return mapToDto(team);
    }

    public TeamDtoResponse createTeam(TeamDtoRequest teamDtoRequest) {
        Team team = Team.builder()
                .name(teamDtoRequest.getName())
                .balance(teamDtoRequest.getBalance())
                .commissionPercentage(teamDtoRequest.getCommissionPercentage())
                .build();

        Team createdTeam = teamRepository.saveTeam(team);
        return mapToDto(createdTeam);
    }

    public TeamDtoResponse updateTeam(Integer id, TeamDtoRequest teamDtoRequest) {
        Team team = teamRepository.getTeamById(id)
                .orElseThrow(() -> new IdNotFoundException(TEAM_NOT_FOUND_MESSAGE + id));

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
            throw new IdNotFoundException(TEAM_NOT_FOUND_MESSAGE + id);
        } else {
            return "Team deleted successfully";
        }
    }

    private TeamDtoResponse mapToDto(Team team) {
        return TeamDtoResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .balance(team.getBalance())
                .commissionPercentage(team.getCommissionPercentage())
                .players(mapPlayersToDto(team.getPlayers()))
                .build();
    }

    private List<PlayerDtoResponse> mapPlayersToDto(List<Player> players) {
        return players.stream()
                .map(player -> PlayerDtoResponse.builder()
                        .id(player.getId())
                        .firstName(player.getFirstName())
                        .lastName(player.getLastName())
                        .age(player.getAge())
                        .experienceMonths(player.getExperienceMonths())
                        .build())
                .collect(Collectors.toList());
    }
}
