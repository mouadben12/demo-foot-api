package com.example.demofoot.controller;

import com.example.demofoot.dto.TeamDTO;
import com.example.demofoot.service.TeamService;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * GET : retourne les équipes avec leurs joueurs
     * - pagination
     * - tri serveur (name, acronym, budget)
     */
    @GetMapping
    public Page<TeamDTO> getTeams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy
    ) {

        Sort sort = Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return teamService.getTeams(pageable);
    }

    /**
     * POST : création équipe avec ou sans joueurs
     */
    @PostMapping
    public TeamDTO createTeam(@RequestBody TeamDTO dto) {
        return teamService.createTeam(dto);
    }
}