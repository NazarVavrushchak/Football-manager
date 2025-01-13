package com.football.manager.service;

import com.football.manager.dto.team.TeamRequestDTO;
import com.football.manager.dto.team.TeamResponseDTO;

import java.util.List;

public interface TeamService {
    TeamResponseDTO createTeam(TeamRequestDTO teamRequestDTO);

    TeamResponseDTO updateTeam(Long id, TeamRequestDTO teamRequestDTO);

    void deleteTeam(Long id);

    TeamResponseDTO getTeamById(Long id);

    List<TeamResponseDTO> getAllTeams();
}