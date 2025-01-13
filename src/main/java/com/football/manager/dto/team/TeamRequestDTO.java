package com.football.manager.dto.team;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamRequestDTO {
    @NotBlank
    @Size(min = 1, max = 100, message = "Team name must be between 1 and 100 characters")
    private String name;

    @DecimalMin(value = "0.00", message = "Commission percentage must be at least 0.00")
    @DecimalMax(value = "10.00", message = "Commission percentage must not exceed 10.00")
    private BigDecimal commissionPercentage;

    @DecimalMin(value = "0.00", message = "Balance must be a non-negative value")
    private BigDecimal balance;
}