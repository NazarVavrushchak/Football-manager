package com.football.manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "commission_percentage", nullable = false)
    @DecimalMin("0.00")
    @DecimalMax("10.00")
    private BigDecimal commissionPercentage;

    @Column(nullable = false)
    @DecimalMin("0.00")
    private BigDecimal balance;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;
}