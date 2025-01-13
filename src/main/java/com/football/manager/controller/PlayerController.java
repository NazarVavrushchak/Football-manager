package com.football.manager.controller;

import com.football.manager.dto.player.PlayerRequestDTO;
import com.football.manager.dto.player.PlayerResponseDTO;
import com.football.manager.service.PlayerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
@AllArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping("/create")
    public ResponseEntity<PlayerResponseDTO> createPlayer(@Valid @RequestBody PlayerRequestDTO playerRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.createPlayer(playerRequestDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PlayerResponseDTO> updatePlayer(
            @PathVariable Long id,
            @Valid @RequestBody PlayerRequestDTO playerRequestDTO) {
        return ResponseEntity.ok(playerService.updatePlayer(id, playerRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> getPlayerById(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @GetMapping
    public ResponseEntity<List<PlayerResponseDTO>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @PostMapping("/{playerId}/transfer/{targetTeamId}")
    public ResponseEntity<String> transferPlayer(@PathVariable Long playerId, @PathVariable Long targetTeamId) {
        playerService.transferPlayer(playerId, targetTeamId);
        return ResponseEntity.ok("Player transferred successfully");
    }
}