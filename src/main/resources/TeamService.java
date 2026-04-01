package com.example.demofoot.service;

import com.example.demofoot.dto.PlayerDTO;
import com.example.demofoot.dto.TeamDTO;
import com.example.demofoot.entity.Player;
import com.example.demofoot.entity.Team;
import com.example.demofoot.exception.TeamNotFoundException;
import com.example.demofoot.repository.PlayerRepository;
import com.example.demofoot.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public TeamDTO createTeam(TeamDTO dto) {
        Team team = TeamMapper.toEntity(dto);
        Team saved = teamRepository.save(team);
        return TeamMapper.toDTO(saved);
    }

    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(TeamMapper::toDTO)
                .toList();
    }

    public TeamDTO getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));

        return TeamMapper.toDTO(team);
    }

    public PlayerDTO  addPlayerToTeam(Long teamId, PlayerDTO dto) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(teamId));

        Player player = PlayerMapper.toEntity(dto);
        player.setTeam(team);

        Player saved = playerRepository.save(player);

        return PlayerMapper.toDTO(saved);
    }

}
