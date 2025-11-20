// src/main/java/com/example/finance_tracker/models/Transaction.java
package com.example.finance_tracker.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String merchant;
    private BigDecimal amount;
    private String category;

    @Column(name = "occurred_at")
    private OffsetDateTime occurredAt;

    private String notes;

    @ElementCollection
    @CollectionTable(name = "transaction_tags", joinColumns = @JoinColumn(name = "transaction_id"))
    @Column(name = "tag")
    private Set<String> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
