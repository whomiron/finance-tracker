package com.example.finance_tracker.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    @Column(name = "target_amount")
    private BigDecimal targetAmount;

    @Column(name = "current_amount")
    private BigDecimal currentAmount;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserProfile owner;
}
