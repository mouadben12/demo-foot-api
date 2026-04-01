package com.example.demofoot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class TeamDTO {

    @NotNull(message = "Name is required")
    @Size(min = 3, max = 50)
    private String name;
    @NotNull
    private String acronym;
    private BigDecimal budget;
    private List<PlayerDTO> players;
}
