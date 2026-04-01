package com.example.demofoot.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;
    @NotNull
    private String position;

}
