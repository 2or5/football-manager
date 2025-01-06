package com.football_manager.service;

import com.football_manager.dto.request.PlayerDtoRequest;
import com.football_manager.dto.response.PlayerTeamDtoResponse;
import com.football_manager.dto.response.TeamDtoResponse;
import com.football_manager.entity.Player;
import com.football_manager.entity.Team;
import com.football_manager.exception.IdNotFoundException;
import com.football_manager.exception.InsufficientBalanceException;
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
    private final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient balance";

    /**
     * Method get all players.
     *
     * @return list of {@link PlayerTeamDtoResponse}.
     */
    public List<PlayerTeamDtoResponse> getAllPlayers() {
        List<Player> players = playerRepository.getAllPlayers();

        return players.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Method get player by id.
     *
     * @param id {@link Integer}
     * @return {@link PlayerTeamDtoResponse}.
     */
    public PlayerTeamDtoResponse getPlayerById(Integer id) {
        Player player = playerRepository.getPlayerById(id)
                .orElseThrow(() -> new IdNotFoundException(PLAYER_NOT_FOUND_MESSAGE + id));

        return mapToDto(player);
    }

    /**
     * Method for create new player.
     *
     * @param playerDtoRequest {@link PlayerDtoRequest}
     * @return {@link PlayerTeamDtoResponse}.
     */
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

    /**
     * Method for update player by id.
     *
     * @param id               {@link Integer}
     * @param playerDtoRequest {@link PlayerDtoRequest}
     * @return {@link PlayerTeamDtoResponse}.
     */
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

    /**
     * Method for delete player by id.
     *
     * @param id {@link Integer}
     * @return {@link String}.
     */
    public String deletePlayer(Integer id) {
        Integer deletedPlayer = playerRepository.deletePlayer(id);
        if (deletedPlayer == 0) {
            throw new IdNotFoundException(PLAYER_NOT_FOUND_MESSAGE + id);
        } else {
            return PLAYER_DELETED_MESSAGE;
        }
    }

    /**
     * Method for conducts transfer for player.
     *
     * @param playerId {@link Integer}
     * @param teamId   {@link Integer}
     * @return {@link PlayerTeamDtoResponse}.
     */
    public PlayerTeamDtoResponse transferPlayer(Integer playerId, Integer teamId) {
        Player player = playerRepository.getPlayerById(playerId)
                .orElseThrow(() -> new IdNotFoundException(PLAYER_NOT_FOUND_MESSAGE + playerId));
        Team toTeam = teamRepository.getTeamById(teamId)
                .orElseThrow(() -> new IdNotFoundException(TEAM_NOT_FOUND_MESSAGE + teamId));

        Double totalTransferCost = calculateTotalTransferCost(player, toTeam);

        if (toTeam.getBalance() < totalTransferCost) {
            throw new InsufficientBalanceException(INSUFFICIENT_BALANCE_MESSAGE);
        }

        Team fromTeam = player.getTeam();
        fromTeam.setBalance(fromTeam.getBalance() + totalTransferCost);
        toTeam.setBalance(toTeam.getBalance() - totalTransferCost);

        player.setTeam(toTeam);
        playerRepository.savePlayer(player);
        return mapToDto(player);
    }

    private Double calculateTotalTransferCost(Player player, Team team) {
        double playerPrice = (player.getExperienceMonths() * 100000.0) / player.getAge();
        double commission = playerPrice * (team.getCommissionPercentage() / 100);
        double totalCost = playerPrice + commission;
        return Math.round(totalCost * 100.0) / 100.0;
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
