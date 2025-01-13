package com.football.manager.service;

import com.football.manager.dto.player.PlayerRequestDTO;
import com.football.manager.dto.player.PlayerResponseDTO;

import java.util.List;

public interface PlayerService {
    PlayerResponseDTO createPlayer(PlayerRequestDTO playerRequestDTO);

    PlayerResponseDTO updatePlayer(Long id, PlayerRequestDTO playerRequestDTO);

    void deletePlayer(Long id);

    PlayerResponseDTO getPlayerById(Long id);

    List<PlayerResponseDTO> getAllPlayers();

    void transferPlayer(Long playerId, Long targetTeamId);
}