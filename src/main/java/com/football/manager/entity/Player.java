package com.football.manager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String surname;

    @Column(nullable = false)
    @Min(16)
    @Max(50)
    private int age;

    @Column(name = "experience_months", nullable = false)
    @Min(0)
    private int experienceMonths;

    @Column(name = "is_in_team", nullable = false)
    private boolean isInTeam;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}