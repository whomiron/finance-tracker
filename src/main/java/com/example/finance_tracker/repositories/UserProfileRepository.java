package com.example.finance_tracker.repositories;

import com.example.finance_tracker.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {}
