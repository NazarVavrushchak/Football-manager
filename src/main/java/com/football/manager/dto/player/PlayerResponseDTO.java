package com.football.manager.dto.player;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerResponseDTO {
    private Long id;
    private String name;
    private String surname;
    private int age;
    private int experienceMonths;
    private boolean isInTeam;
    private String teamName;
}
