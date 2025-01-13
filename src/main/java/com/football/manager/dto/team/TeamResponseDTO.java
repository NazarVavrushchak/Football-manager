package com.football.manager.dto.team;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamResponseDTO {
    private Long id;
    private String name;
    private BigDecimal commissionPercentage;
    private BigDecimal balance;
}
