package com.example.finance_tracker.forms;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionForm {
    private Long userId;
    private String merchant;
    private BigDecimal amount;
    private String category;
    private LocalDateTime occurredAt;
    private String notes;
    private String tagsInput;
}
