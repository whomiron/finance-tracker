package com.example.finance_tracker.servicies;

import com.example.finance_tracker.models.Goal;
import com.example.finance_tracker.repositories.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {
    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> list(Long userId) {
        return goalRepository.findByOwnerId(userId);
    }
}
