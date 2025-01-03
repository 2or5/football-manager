package com.football_manager.service;

import com.football_manager.dto.request.PlayerDtoRequest;
import com.football_manager.dto.response.PlayerDtoResponse;
import com.football_manager.dto.response.TeamDtoResponse;
import com.football_manager.entity.Player;
import com.football_manager.entity.Team;
import com.football_manager.exception.IdNotFoundException;
import com.football_manager.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlayerService {

    private final String PLAYER_NOT_FOUND_MESSAGE = "The player does not exist by this id: ";

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<PlayerDtoResponse> getAllPlayers() {
        List<Player> players = playerRepository.getAllPlayers();

        return players.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PlayerDtoResponse getPlayerById(Integer id) {
        Player player = playerRepository.getPlayerById(id)
                .orElseThrow(() -> new IdNotFoundException(PLAYER_NOT_FOUND_MESSAGE + id));

        return mapToDto(player);
    }

    public PlayerDtoResponse createPlayer(PlayerDtoRequest playerDtoRequest) {
        Player player = Player.builder()
                .birthDate(playerDtoRequest.getBirthDate())
                .firstName(playerDtoRequest.getFirstName())
                .lastName(playerDtoRequest.getLastName())
                .experienceMonths(playerDtoRequest.getExperienceMonths())
                .build();

        Player createdPlayer = playerRepository.savePlayer(player);
        return mapToDto(createdPlayer);
    }

    public PlayerDtoResponse updatePlayer(Integer id, @Valid PlayerDtoRequest playerDtoRequest) {
        Player player = playerRepository.getPlayerById(id)
                .orElseThrow(() -> new IdNotFoundException(PLAYER_NOT_FOUND_MESSAGE + id));

        Player playerToBeUpdated = player.toBuilder()
                .firstName(playerDtoRequest.getFirstName())
                .lastName(playerDtoRequest.getLastName())
                .birthDate(playerDtoRequest.getBirthDate())
                .experienceMonths(playerDtoRequest.getExperienceMonths())
                .build();

        Player updatedPlayer = playerRepository.updatePlayer(playerToBeUpdated);
        return mapToDto(updatedPlayer);
    }

    public String deletePlayer(Integer id) {
        Integer deletedPlayer = playerRepository.deletePlayer(id);
        if (deletedPlayer == 0) {
            throw new IdNotFoundException(PLAYER_NOT_FOUND_MESSAGE + id);
        } else {
            return "Player deleted successfully";
        }
    }

    private PlayerDtoResponse mapToDto(Player player) {
        return PlayerDtoResponse.builder()
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
