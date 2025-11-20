package com.example.finance_tracker.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@Setter
@Entity
@Table(name = "budgets")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = YearMonthAttributeConverter.class)
    private YearMonth period;

    private String category;
    private BigDecimal limitAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserProfile owner;
}
