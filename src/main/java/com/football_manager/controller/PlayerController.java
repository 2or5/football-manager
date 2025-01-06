package com.football_manager.controller;

import com.football_manager.dto.request.PlayerDtoRequest;
import com.football_manager.dto.response.PlayerTeamDtoResponse;
import com.football_manager.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /**
     * The controller which conducts transfer for player.
     *
     * @param playerId {@link Integer}
     * @param teamId   {@link Integer}
     * @return {@link PlayerTeamDtoResponse}.
     */
    @PostMapping("/{playerId}/transfer/{teamId}")
    public ResponseEntity<PlayerTeamDtoResponse> transferPlayer(
            @PathVariable Integer playerId,
            @PathVariable Integer teamId) {
        return ResponseEntity.ok(playerService.transferPlayer(playerId, teamId));
    }

    /**
     * The controller which returns all players.
     *
     * @return list of {@link PlayerTeamDtoResponse}.
     */
    @GetMapping
    public ResponseEntity<List<PlayerTeamDtoResponse>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    /**
     * The controller which return player by id.
     *
     * @param id {@link Integer}
     * @return list of {@link PlayerTeamDtoResponse}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlayerTeamDtoResponse> getPlayer(@PathVariable Integer id) {
        return ResponseEntity.ok(playerService.getPlayer(id));
    }

    /**
     * The controller which create new player.
     *
     * @param playerDtoRequest {@link PlayerDtoRequest}
     * @return {@link PlayerTeamDtoResponse}.
     */
    @PostMapping
    public ResponseEntity<PlayerTeamDtoResponse> createPlayer(@Valid @RequestBody PlayerDtoRequest playerDtoRequest) {
        return ResponseEntity.ok(playerService.createPlayer(playerDtoRequest));
    }

    /**
     * The controller which update player.
     *
     * @param id               {@link Integer}
     * @param playerDtoRequest {@link PlayerDtoRequest}
     * @return {@link PlayerTeamDtoResponse}.
     */
    @PatchMapping("{id}")
    public ResponseEntity<PlayerTeamDtoResponse> updatePlayer(
            @PathVariable Integer id,
            @Valid @RequestBody PlayerDtoRequest playerDtoRequest) {
        return ResponseEntity.ok(playerService.updatePlayer(id, playerDtoRequest));
    }

    /**
     * The controller which delete player.
     *
     * @param id {@link Integer}
     * @return {@link String}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Integer id) {
        return ResponseEntity.ok(playerService.deletePlayer(id));
    }
}
