package com.example.demofoot.service;

import com.example.demofoot.dto.TeamDTO;
import com.example.demofoot.entity.Team;
import com.example.demofoot.exception.BadRequestException;
import com.example.demofoot.exception.ResourceNotFoundException;
import com.example.demofoot.mapper.TeamMapper;
import com.example.demofoot.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private static final List<String> ALLOWED_SORT = List.of(
            "name", "acronym", "budget"
    );

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    // GET ALL
    public Page<TeamDTO> getTeams(Pageable pageable) {

        validateSort(pageable);

        return teamRepository.findAll(pageable)
                .map(TeamMapper::toDto);
    }

    // GET BY ID
    public TeamDTO getTeamById(Long id) {

        Team team = teamRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Team not found with id: " + id)
                );

        return TeamMapper.toDto(team);
    }

    // CREATE
    public TeamDTO createTeam(TeamDTO teamDTO) {

        validateTeam(teamDTO);

        Team team = TeamMapper.toEntity(teamDTO);

        Team savedTeam = teamRepository.save(team);

        return TeamMapper.toDto(savedTeam);
    }

    // ================= VALIDATION =================

    private void validateSort(Pageable pageable) {

        String sortProperty = pageable.getSort().stream()
                .map(Sort.Order::getProperty)
                .findFirst()
                .orElse("name");

        if (!ALLOWED_SORT.contains(sortProperty)) {
            throw new BadRequestException("Tri non autorisé : " + sortProperty);
        }
    }

    private void validateTeam(TeamDTO teamDTO) {

        if (teamDTO == null) {
            throw new BadRequestException("TeamDTO cannot be null");
        }

        if (teamDTO.getName() == null || teamDTO.getName().isBlank()
                || teamDTO.getAcronym() == null || teamDTO.getAcronym().isBlank()
                || teamDTO.getBudget() == null) {

            throw new BadRequestException("Champs obligatoires manquants");
        }
    }
}