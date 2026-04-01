package com.example.demofoot.integration;

import com.example.demofoot.dto.TeamDTO;
import com.example.demofoot.entity.Team;
import com.example.demofoot.repository.TeamRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class TeamControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // CREATE TEAM
    @Test
    @Order(1)
    @SneakyThrows
    void should_create_team_in_database(){

        TeamDTO dto = new TeamDTO();
        dto.setName("OGC Nice");
        dto.setAcronym("OGCN");
        dto.setBudget(new BigDecimal("1000000"));

        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("OGC Nice"));

        assertEquals(1, teamRepository.findAll().size());
    }

    @Test
    @Order(2)
    @SneakyThrows
    void should_get_team_by_id() {

        Team team = new Team();
        team.setName("PSG");
        team.setAcronym("PSG");
        team.setBudget(new BigDecimal("500000000"));

        team = teamRepository.save(team);

        mockMvc.perform(get("/api/teams/" + team.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("PSG"))
                .andExpect(jsonPath("$.acronym").value("PSG"));
    }

    @Test
    @Order(3)
    @SneakyThrows
    void should_return_paginated_teams() {

        for (int i = 1; i <= 5; i++) {
            Team team = new Team();
            team.setName("Team " + i);
            team.setAcronym("T" + i);
            team.setBudget(new BigDecimal("1000000"));
            teamRepository.save(team);
        }

        mockMvc.perform(get("/api/teams?page=0&size=3&sortBy=name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3));
    }

    @Test
    @Order(4)
    @SneakyThrows
    void should_fail_when_sort_invalid() {

        mockMvc.perform(get("/api/teams?sortBy=invalid"))
                .andExpect(status().isBadRequest());
    }

}






