package com.example.demofoot.service;

import com.example.demofoot.dto.TeamDTO;
import com.example.demofoot.entity.Team;
import com.example.demofoot.exception.BadRequestException;
import com.example.demofoot.exception.ResourceNotFoundException;
import com.example.demofoot.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    //GET ALL

    @Test
    void shouldReturnTeamsPage() {

        Team team = new Team();
        team.setName("PSG");
        team.setAcronym("PSG");
        team.setBudget(BigDecimal.valueOf(100));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

        when(teamRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(team)));

        Page<TeamDTO> result = teamService.getTeams(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals("PSG", result.getContent().get(0).getName());

        verify(teamRepository, times(1)).findAll(pageable);
    }

    //GET BY ID

    @Test
    void shouldReturnTeamById() {

        Team team = new Team();
        team.setId(1L);
        team.setName("OM");
        team.setAcronym("OM");
        team.setBudget(BigDecimal.valueOf(50));

        when(teamRepository.findById(1L))
                .thenReturn(Optional.of(team));

        TeamDTO result = teamService.getTeamById(1L);

        assertEquals("OM", result.getName());
        assertEquals("OM", result.getAcronym());
    }

    @Test
    void shouldThrowWhenTeamNotFound() {

        when(teamRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> teamService.getTeamById(1L));
    }

    //CREATE

    @Test
    void shouldCreateTeam() {

        TeamDTO dto = new TeamDTO();
        dto.setName("Nice");
        dto.setAcronym("OGCN");
        dto.setBudget(BigDecimal.valueOf(30));

        Team saved = new Team();
        saved.setId(1L);
        saved.setName("Nice");
        saved.setAcronym("OGCN");
        saved.setBudget(BigDecimal.valueOf(30));

        when(teamRepository.save(any(Team.class)))
                .thenReturn(saved);

        TeamDTO result = teamService.createTeam(dto);

        assertEquals("Nice", result.getName());
        assertEquals("OGCN", result.getAcronym());

        verify(teamRepository, times(1)).save(any(Team.class));
    }

    //VALIDATION

    @Test
    void shouldThrowWhenTeamIsNull() {

        assertThrows(BadRequestException.class,
                () -> teamService.createTeam(null));
    }

    @Test
    void shouldThrowWhenTeamInvalid() {

        TeamDTO dto = new TeamDTO();

        assertThrows(BadRequestException.class,
                () -> teamService.createTeam(dto));
    }
}