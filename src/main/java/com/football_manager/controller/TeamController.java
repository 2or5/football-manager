package com.football_manager.controller;

import com.football_manager.dto.response.TeamDtoResponse;
import com.football_manager.dto.request.TeamDtoRequest;
import com.football_manager.service.TeamService;
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
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamDtoResponse>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDtoResponse> getTeam(@PathVariable Integer id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @PostMapping
    public ResponseEntity<TeamDtoResponse> createTeam(@Valid @RequestBody TeamDtoRequest teamDtoRequest) {
        return ResponseEntity.ok(teamService.createTeam(teamDtoRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeamDtoResponse> updateTeam(@PathVariable Integer id,
                                                      @Valid @RequestBody TeamDtoRequest teamDtoRequest) {
        return ResponseEntity.ok(teamService.updateTeam(id, teamDtoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable Integer id) {
        return ResponseEntity.ok(teamService.deleteTeam(id));
    }
}
