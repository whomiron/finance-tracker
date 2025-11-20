package com.example.finance_tracker.forms;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class GoalForm {
    private String label;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private LocalDate targetDate;
}
