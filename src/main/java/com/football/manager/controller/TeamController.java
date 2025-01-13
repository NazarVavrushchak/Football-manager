package com.football.manager.controller;

import com.football.manager.dto.team.TeamRequestDTO;
import com.football.manager.dto.team.TeamResponseDTO;
import com.football.manager.service.TeamService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@AllArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/create")
    public ResponseEntity<TeamResponseDTO> createTeam(@Valid @RequestBody TeamRequestDTO teamRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.createTeam(teamRequestDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TeamResponseDTO> updateTeam(
            @PathVariable Long id,
            @Valid @RequestBody TeamRequestDTO teamRequestDTO) {
        return ResponseEntity.ok(teamService.updateTeam(id, teamRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }
}