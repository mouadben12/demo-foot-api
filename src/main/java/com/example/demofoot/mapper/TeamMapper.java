package com.example.demofoot.mapper;

import com.example.demofoot.dto.TeamDTO;
import com.example.demofoot.entity.Team;

public class TeamMapper {

    public static TeamDTO toDto(Team team) {
        if (team == null) return null;

        TeamDTO dto = new TeamDTO();
        dto.setName(team.getName());
        dto.setAcronym(team.getAcronym());
        dto.setBudget(team.getBudget());
        return dto;
    }

    public static Team toEntity(TeamDTO dto) {
        if (dto == null) return null;

        Team team = new Team();
        team.setName(dto.getName());
        team.setAcronym(dto.getAcronym());
        team.setBudget(dto.getBudget());
        return team;
    }
}