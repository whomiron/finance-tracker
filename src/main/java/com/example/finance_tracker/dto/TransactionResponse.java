package com.example.finance_tracker.dto;

import com.example.finance_tracker.models.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private String merchant;
    private BigDecimal amount;
    private String category;
    private OffsetDateTime occurredAt;
    private String notes;
    private Set<String> tags;
}
