package com.football_manager.service;

import com.football_manager.dto.request.TeamDtoRequest;
import com.football_manager.dto.response.PlayerResponse;
import com.football_manager.dto.response.TeamDtoResponse;
import com.football_manager.dto.response.TeamPlayerDtoResponse;
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
    private final String TEAM_DELETED_MESSAGE = "Team deleted successfully";


    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    /**
     * Method get all teams.
     *
     * @return list of {@link TeamPlayerDtoResponse}.
     */
    public List<TeamPlayerDtoResponse> getAllTeams() {
        List<Team> teams = teamRepository.getAllTeams();

        return teams.stream()
                .map(this::mapToTeamPlayerDto)
                .collect(Collectors.toList());
    }

    /**
     * Method get team by id.
     *
     * @param id {@link Integer}
     * @return {@link TeamPlayerDtoResponse}.
     */
    public TeamPlayerDtoResponse getTeam(Integer id) {
        Team team = getTeamById(id);
        return mapToTeamPlayerDto(team);
    }

    /**
     * Method for create new team.
     *
     * @param teamDtoRequest {@link TeamDtoRequest}
     * @return {@link TeamDtoResponse}.
     */
    public TeamDtoResponse createTeam(TeamDtoRequest teamDtoRequest) {
        Team team = Team.builder()
                .name(teamDtoRequest.getName())
                .balance(teamDtoRequest.getBalance())
                .commissionPercentage(teamDtoRequest.getCommissionPercentage())
                .build();

        Team createdTeam = teamRepository.saveTeam(team);
        return mapToTeamDto(createdTeam);
    }

    /**
     * Method for update team by id.
     *
     * @param id             {@link Integer}
     * @param teamDtoRequest {@link TeamDtoRequest}
     * @return {@link TeamDtoResponse}.
     */
    public TeamDtoResponse updateTeam(Integer id, TeamDtoRequest teamDtoRequest) {
        Team team = getTeamById(id);

        Team teamToBeUpdated = team.toBuilder()
                .name(teamDtoRequest.getName())
                .balance(teamDtoRequest.getBalance())
                .commissionPercentage(teamDtoRequest.getCommissionPercentage())
                .build();

        Team updatedTeam = teamRepository.updateTeam(teamToBeUpdated);
        return mapToTeamDto(updatedTeam);
    }

    /**
     * Method for delete team by id.
     *
     * @param id {@link Integer}
     * @return {@link String}.
     */
    public String deleteTeam(Integer id) {
        Integer deletedTeam = teamRepository.deleteTeam(id);
        if (deletedTeam == 0) {
            throw new IdNotFoundException(TEAM_NOT_FOUND_MESSAGE + id);
        } else {
            return TEAM_DELETED_MESSAGE;
        }
    }

    public Team getTeamById(Integer id) {
        return teamRepository.getTeamById(id)
                .orElseThrow(() -> new IdNotFoundException(TEAM_NOT_FOUND_MESSAGE + id));
    }

    private TeamDtoResponse mapToTeamDto(Team team) {
        return TeamDtoResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .balance(team.getBalance())
                .commissionPercentage(team.getCommissionPercentage())
                .build();
    }

    private TeamPlayerDtoResponse mapToTeamPlayerDto(Team team) {
        return TeamPlayerDtoResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .balance(team.getBalance())
                .commissionPercentage(team.getCommissionPercentage())
                .players(mapPlayersToDto(team.getPlayers()))
                .build();
    }

    private List<PlayerResponse> mapPlayersToDto(List<Player> players) {
        return players.stream()
                .map(player -> PlayerResponse.builder()
                        .id(player.getId())
                        .firstName(player.getFirstName())
                        .lastName(player.getLastName())
                        .age(player.getAge())
                        .experienceMonths(player.getExperienceMonths())
                        .build())
                .collect(Collectors.toList());
    }
}
