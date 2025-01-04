package com.football_manager.service;

import com.football_manager.dto.request.PlayerDtoRequest;
import com.football_manager.dto.response.PlayerTeamDtoResponse;
import com.football_manager.dto.response.TeamDtoResponse;
import com.football_manager.entity.Player;
import com.football_manager.entity.Team;
import com.football_manager.exception.IdNotFoundException;
import com.football_manager.repository.PlayerRepository;
import com.football_manager.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    private final String PLAYER_NOT_FOUND_MESSAGE = "The player does not exist by this id: ";
    private final String TEAM_NOT_FOUND_MESSAGE = "The team does not exist by this id: ";
    private final String PLAYER_DELETED_MESSAGE = "Player deleted successfully";

    public List<PlayerTeamDtoResponse> getAllPlayers() {
        List<Player> players = playerRepository.getAllPlayers();

        return players.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PlayerTeamDtoResponse getPlayerById(Integer id) {
        Player player = playerRepository.getPlayerById(id)
                .orElseThrow(() -> new IdNotFoundException(PLAYER_NOT_FOUND_MESSAGE + id));

        return mapToDto(player);
    }

    public PlayerTeamDtoResponse createPlayer(PlayerDtoRequest playerDtoRequest) {
        Team team = teamRepository.getTeamById(playerDtoRequest.getTeamId())
                .orElseThrow(() -> new IdNotFoundException(TEAM_NOT_FOUND_MESSAGE + playerDtoRequest.getTeamId()));

        Player player = Player.builder()
                .birthDate(playerDtoRequest.getBirthDate())
                .firstName(playerDtoRequest.getFirstName())
                .lastName(playerDtoRequest.getLastName())
                .experienceMonths(playerDtoRequest.getExperienceMonths())
                .team(team)
                .build();

        Player createdPlayer = playerRepository.savePlayer(player);
        return mapToDto(createdPlayer);
    }

    public PlayerTeamDtoResponse updatePlayer(Integer id, PlayerDtoRequest playerDtoRequest) {
        Team team = teamRepository.getTeamById(playerDtoRequest.getTeamId())
                .orElseThrow(() -> new IdNotFoundException(TEAM_NOT_FOUND_MESSAGE + playerDtoRequest.getTeamId()));
        Player player = playerRepository.getPlayerById(id)
                .orElseThrow(() -> new IdNotFoundException(PLAYER_NOT_FOUND_MESSAGE + id));

        Player playerToBeUpdated = player.toBuilder()
                .firstName(playerDtoRequest.getFirstName())
                .lastName(playerDtoRequest.getLastName())
                .birthDate(playerDtoRequest.getBirthDate())
                .experienceMonths(playerDtoRequest.getExperienceMonths())
                .team(team)
                .build();

        Player updatedPlayer = playerRepository.updatePlayer(playerToBeUpdated);
        return mapToDto(updatedPlayer);
    }

    public String deletePlayer(Integer id) {
        Integer deletedPlayer = playerRepository.deletePlayer(id);
        if (deletedPlayer == 0) {
            throw new IdNotFoundException(PLAYER_NOT_FOUND_MESSAGE + id);
        } else {
            return PLAYER_DELETED_MESSAGE;
        }
    }

    private PlayerTeamDtoResponse mapToDto(Player player) {
        return PlayerTeamDtoResponse.builder()
                .id(player.getId())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .age(player.getAge())
                .experienceMonths(player.getExperienceMonths())
                .team(mapToTeamDto(player.getTeam()))
                .build();
    }

    private TeamDtoResponse mapToTeamDto(Team team) {
        return team == null ? null : TeamDtoResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .balance(team.getBalance())
                .commissionPercentage(team.getCommissionPercentage())
                .build();
    }
}
