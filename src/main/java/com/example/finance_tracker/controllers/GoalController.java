// src/main/java/com/example/finance_tracker/controllers/GoalController.java
package com.example.finance_tracker.controllers;

import com.example.finance_tracker.forms.GoalForm;
import com.example.finance_tracker.models.Goal;
import com.example.finance_tracker.models.UserProfile;
import com.example.finance_tracker.repositories.GoalRepository;
import com.example.finance_tracker.repositories.UserProfileRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/{userId}/goals")
public class GoalController {

    private final GoalRepository goalRepository;
    private final UserProfileRepository userProfileRepository;

    public GoalController(GoalRepository goalRepository,
                          UserProfileRepository userProfileRepository) {
        this.goalRepository = goalRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @GetMapping
    public String list(@PathVariable Long userId, Model model) {
        List<Goal> goals = goalRepository.findByOwnerId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("goals", goals);
        model.addAttribute("goalForm", new GoalForm());
        return "goals";
    }

    @PostMapping
    public String create(@PathVariable Long userId, @ModelAttribute GoalForm form) {
        UserProfile owner = userProfileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Goal goal = new Goal();
        goal.setOwner(owner);
        goal.setLabel(form.getLabel());
        goal.setTargetAmount(form.getTargetAmount());
        goal.setCurrentAmount(form.getCurrentAmount());
        goal.setTargetDate(form.getTargetDate());

        goalRepository.save(goal);
        return "redirect:/" + userId + "/goals";
    }
}
