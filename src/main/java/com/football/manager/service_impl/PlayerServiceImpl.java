package com.football.manager.service_impl;

import com.football.manager.dto.player.PlayerRequestDTO;
import com.football.manager.dto.player.PlayerResponseDTO;
import com.football.manager.entity.Player;
import com.football.manager.entity.Team;
import com.football.manager.exception.ConflictException;
import com.football.manager.exception.InsufficientFundsException;
import com.football.manager.exception.PlayerNotFoundException;
import com.football.manager.exception.TeamNotFoundException;
import com.football.manager.repository.PlayerRepo;
import com.football.manager.repository.TeamRepo;
import com.football.manager.service.PlayerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepo playerRepository;
    private final TeamRepo teamRepository;

    @Override
    public PlayerResponseDTO createPlayer(PlayerRequestDTO playerRequestDTO) {
        Player player = mapToEntity(playerRequestDTO, null);
        if (playerRequestDTO.getTeamId() != null) {
            Team team = teamRepository.findById(playerRequestDTO.getTeamId())
                    .orElseThrow(() -> new TeamNotFoundException("Team not found"));
            player.setTeam(team);
        }
        Player savedPlayer = playerRepository.save(player);
        return mapToResponseDTO(savedPlayer);
    }

    @Override
    public PlayerResponseDTO updatePlayer(Long id, PlayerRequestDTO playerRequestDTO) {
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found"));
        existingPlayer.setName(playerRequestDTO.getName());
        existingPlayer.setSurname(playerRequestDTO.getSurname());
        existingPlayer.setAge(playerRequestDTO.getAge());
        existingPlayer.setExperienceMonths(playerRequestDTO.getExperienceMonths());
        existingPlayer.setInTeam(playerRequestDTO.isInTeam());

        if (playerRequestDTO.getTeamId() != null) {
            Team team = teamRepository.findById(playerRequestDTO.getTeamId())
                    .orElseThrow(() -> new TeamNotFoundException("Team not found"));
            existingPlayer.setTeam(team);
        } else {
            existingPlayer.setTeam(null);
        }

        Player updatedPlayer = playerRepository.save(existingPlayer);
        return mapToResponseDTO(updatedPlayer);
    }

    @Override
    public void deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new PlayerNotFoundException("Player not found");
        }
        playerRepository.deleteById(id);
    }

    @Override
    public PlayerResponseDTO getPlayerById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found"));
        return mapToResponseDTO(player);
    }

    @Override
    public List<PlayerResponseDTO> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void transferPlayer(Long playerId, Long targetTeamId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found"));

        Team targetTeam = teamRepository.findById(targetTeamId)
                .orElseThrow(() -> new TeamNotFoundException("Target team not found"));

        if (player.getTeam() != null) {
            Team currentTeam = player.getTeam();

            if (currentTeam.getId().equals(targetTeam.getId())) {
                throw new ConflictException("Player is already in the target team");
            }

            BigDecimal transferCost = BigDecimal.valueOf(player.getExperienceMonths())
                    .multiply(BigDecimal.valueOf(100000))
                    .divide(BigDecimal.valueOf(player.getAge()), 2, BigDecimal.ROUND_HALF_UP);

            BigDecimal commission = transferCost.multiply(currentTeam.getCommissionPercentage()
                    .divide(BigDecimal.valueOf(100)));

            BigDecimal totalCost = transferCost.add(commission);

            if (targetTeam.getBalance().compareTo(totalCost) < 0) {
                throw new InsufficientFundsException("Target team does not have enough balance for the transfer");
            }

            targetTeam.setBalance(targetTeam.getBalance().subtract(totalCost));
            currentTeam.setBalance(currentTeam.getBalance().add(totalCost));
        }

        player.setTeam(targetTeam);
        targetTeam.getPlayers().add(player);

        teamRepository.save(targetTeam);
        playerRepository.save(player);
    }


    private Player mapToEntity(PlayerRequestDTO dto, Team team) {
        return Player.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .age(dto.getAge())
                .experienceMonths(dto.getExperienceMonths())
                .isInTeam(dto.isInTeam())
                .team(team)
                .build();
    }

    private PlayerResponseDTO mapToResponseDTO(Player player) {
        return PlayerResponseDTO.builder()
                .id(player.getId())
                .name(player.getName())
                .surname(player.getSurname())
                .age(player.getAge())
                .experienceMonths(player.getExperienceMonths())
                .isInTeam(player.isInTeam())
                .teamName(player.getTeam() != null ? player.getTeam().getName() : null)
                .build();
    }
}