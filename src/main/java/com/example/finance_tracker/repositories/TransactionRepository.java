package com.example.finance_tracker.repositories;

import com.example.finance_tracker.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountOwnerIdAndOccurredAtBetween(Long ownerId, OffsetDateTime start, OffsetDateTime end);
    List<Transaction> findByAccountId(Long accountId);
}
