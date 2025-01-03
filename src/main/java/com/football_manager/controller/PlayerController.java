package com.football_manager.controller;

import com.football_manager.dto.request.PlayerDtoRequest;
import com.football_manager.dto.response.PlayerDtoResponse;
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

    @GetMapping
    public ResponseEntity<List<PlayerDtoResponse>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDtoResponse> getPlayer(@PathVariable Integer id) {
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @PostMapping
    public ResponseEntity<PlayerDtoResponse> createPlayer(@Valid @RequestBody PlayerDtoRequest playerDtoRequest) {
        return ResponseEntity.ok(playerService.createPlayer(playerDtoRequest));
    }

    @PatchMapping("{id}")
    public ResponseEntity<PlayerDtoResponse> updatePlayer(
            @PathVariable Integer id,
            @Valid @RequestBody PlayerDtoRequest playerDtoRequest) {
        return ResponseEntity.ok(playerService.updatePlayer(id, playerDtoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable Integer id) {
        return ResponseEntity.ok(playerService.deletePlayer(id));
    }
}
