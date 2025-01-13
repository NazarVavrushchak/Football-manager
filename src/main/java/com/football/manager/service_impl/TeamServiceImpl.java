package com.football.manager.service_impl;

import com.football.manager.dto.team.TeamRequestDTO;
import com.football.manager.dto.team.TeamResponseDTO;
import com.football.manager.entity.Team;
import com.football.manager.exception.TeamNotFoundException;
import com.football.manager.repository.TeamRepo;
import com.football.manager.service.TeamService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepo teamRepository;

    @Override
    public TeamResponseDTO createTeam(TeamRequestDTO teamRequestDTO) {
        Team team = mapToEntity(teamRequestDTO);
        Team savedTeam = teamRepository.save(team);
        return mapToResponseDTO(savedTeam);
    }

    @Override
    public TeamResponseDTO updateTeam(Long id, TeamRequestDTO teamRequestDTO) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException("Team not found"));

        existingTeam.setName(teamRequestDTO.getName());
        existingTeam.setCommissionPercentage(teamRequestDTO.getCommissionPercentage());
        existingTeam.setBalance(teamRequestDTO.getBalance());

        Team updatedTeam = teamRepository.save(existingTeam);
        return mapToResponseDTO(updatedTeam);
    }

    @Override
    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new TeamNotFoundException("Team not found");
        }
        teamRepository.deleteById(id);
    }

    @Override
    public TeamResponseDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException("Team not found"));
        return mapToResponseDTO(team);
    }

    @Override
    public List<TeamResponseDTO> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private Team mapToEntity(TeamRequestDTO dto) {
        return Team.builder()
                .name(dto.getName())
                .commissionPercentage(dto.getCommissionPercentage())
                .balance(dto.getBalance())
                .build();
    }

    private TeamResponseDTO mapToResponseDTO(Team team) {
        return TeamResponseDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .commissionPercentage(team.getCommissionPercentage())
                .balance(team.getBalance())
                .build();
    }
}