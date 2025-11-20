package com.example.finance_tracker.repositories;

import com.example.finance_tracker.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByOwnerId(Long ownerId);
}
