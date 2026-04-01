package com.example.demofoot.service;

import com.example.demofoot.dto.PlayerDTO;
import com.example.demofoot.dto.TeamDTO;
import com.example.demofoot.entity.Player;
import com.example.demofoot.entity.Team;
import com.example.demofoot.exception.TeamNotFoundException;
import com.example.demofoot.repository.PlayerRepository;
import com.example.demofoot.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private TeamService teamService;

    @Test
    void TestShouldReturnTeam_whenIdExists() {
        Team team = new Team();
        team.setId(1L);
        team.setName("Nice");

        Mockito.when(teamRepository.findById(1L))
                .thenReturn(Optional.of(team));

        TeamDTO result = teamService.getTeamById(1L);

        Assertions.assertEquals("Nice", result.getName());
    }

    @Test
    void TestShouldThrowException_whenTeamNotFound() {
        Mockito.when(teamRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(TeamNotFoundException.class,
                () -> teamService.getTeamById(1L));
    }

    @Test
    void TestShouldCreateTeam() {
        TeamDTO dto = new TeamDTO();
        dto.setName("Nice");
        dto.setAcronym("OGCN");

        Team team = new Team();
        team.setId(1L);
        team.setName("Nice");

        Mockito.when(teamRepository.save(Mockito.any(Team.class)))
                .thenReturn(team);

        TeamDTO result = teamService.createTeam(dto);

        Assertions.assertEquals("Nice", result.getName());
    }

    @Test
    void TestShouldReturnAllTeams() {

        Team team1 = new Team();
        team1.setName("Nice");

        Team team2 = new Team();
        team2.setName("PSG");

        Mockito.when(teamRepository.findAll())
                .thenReturn(List.of(team1, team2));

        List<TeamDTO> result = teamService.getAllTeams();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void TestShouldReturnTeamById() {

        Team team = new Team();
        team.setId(1L);
        team.setName("Nice");

        Mockito.when(teamRepository.findById(1L))
                .thenReturn(Optional.of(team));

        TeamDTO result = teamService.getTeamById(1L);

        Assertions.assertEquals("Nice", result.getName());
    }

    @Test
    void shouldAddPlayerToTeam() {

        Team team = new Team();
        team.setId(1L);
        team.setBudget(BigDecimal.valueOf(10_000_000));

        Player player = new Player();
        player.setName("Mbappe");

        Mockito.when(teamRepository.findById(1L))
                .thenReturn(Optional.of(team));

        Mockito.when(playerRepository.save(Mockito.any(Player.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PlayerDTO result = teamService.addPlayerToTeam(1L, PlayerMapper.toDTO(player));

        Assertions.assertEquals("Mbappe", result.getName());
    }

}
