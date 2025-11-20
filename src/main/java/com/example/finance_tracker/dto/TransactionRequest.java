package com.example.finance_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    @NotBlank
    private String merchant;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotBlank
    private String category;

    @NotNull
    private OffsetDateTime occurredAt;

    private String notes;
    private Set<@NotBlank String> tags;
}
