package com.example.finance_tracker.controllers;

import com.example.finance_tracker.servicies.RecommendationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/{userId}/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public String show(@PathVariable Long userId, Model model) {
        model.addAttribute("recommendations", recommendationService.generate(userId));
        model.addAttribute("userId", userId);
        return "recommendations";
    }
}
