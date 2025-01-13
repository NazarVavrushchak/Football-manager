package com.football.manager.dto.player;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerRequestDTO {
    @NotBlank
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @NotBlank
    @Size(min = 1, max = 100, message = "Surname must be between 1 and 100 characters")
    private String surname;

    @Min(value = 16, message = "Age must be at least 16")
    @Max(value = 50, message = "Age must not exceed 50")
    private int age;

    @Min(value = 0, message = "Experience months must be a non-negative value")
    private int experienceMonths;

    private boolean isInTeam;

    private Long teamId;
}
