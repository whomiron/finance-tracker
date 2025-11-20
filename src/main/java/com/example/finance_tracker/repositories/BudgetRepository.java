package com.example.finance_tracker.repositories;

import com.example.finance_tracker.models.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByOwnerIdAndPeriod(Long ownerId, YearMonth period);
}
