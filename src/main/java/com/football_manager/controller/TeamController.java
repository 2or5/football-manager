package com.football_manager.controller;

import com.football_manager.dto.response.TeamPlayerDtoResponse;
import com.football_manager.dto.request.TeamDtoRequest;
import com.football_manager.dto.response.TeamDtoResponse;
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

    /**
     * The controller which returns all teams.
     *
     * @return list of {@link TeamPlayerDtoResponse}.
     */
    @GetMapping
    public ResponseEntity<List<TeamPlayerDtoResponse>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    /**
     * The controller which return team by id.
     *
     * @param id {@link Integer}
     * @return team {@link TeamPlayerDtoResponse}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TeamPlayerDtoResponse> getTeam(@PathVariable Integer id) {
        return ResponseEntity.ok(teamService.getTeam(id));
    }

    /**
     * The controller which create new team.
     *
     * @param teamDtoRequest {@link TeamDtoRequest}
     * @return {@link TeamDtoResponse}.
     */
    @PostMapping
    public ResponseEntity<TeamDtoResponse> createTeam(@Valid @RequestBody TeamDtoRequest teamDtoRequest) {
        return ResponseEntity.ok(teamService.createTeam(teamDtoRequest));
    }

    /**
     * The controller which update team.
     *
     * @param id             {@link Integer}
     * @param teamDtoRequest {@link TeamDtoRequest}
     * @return {@link TeamDtoResponse}.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<TeamDtoResponse> updateTeam(
            @PathVariable Integer id,
            @Valid @RequestBody TeamDtoRequest teamDtoRequest) {
        return ResponseEntity.ok(teamService.updateTeam(id, teamDtoRequest));
    }

    /**
     * The controller which delete team.
     *
     * @param id {@link Integer}
     * @return {@link String}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeam(@PathVariable Integer id) {
        return ResponseEntity.ok(teamService.deleteTeam(id));
    }
}
