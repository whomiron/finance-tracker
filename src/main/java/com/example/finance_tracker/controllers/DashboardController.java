package com.example.finance_tracker.controllers;

import com.example.finance_tracker.config.UserDetailsImpl;
import com.example.finance_tracker.servicies.BudgetService;
import com.example.finance_tracker.servicies.RecommendationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.YearMonth;

@Controller
public class DashboardController {

    private final BudgetService budgetService;
    private final RecommendationService recommendationService;

    public DashboardController(BudgetService budgetService,
                               RecommendationService recommendationService) {
        this.budgetService = budgetService;
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}/dashboard")
    public String home(@PathVariable Long userId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UserDetailsImpl)) {
            return "redirect:/login";
        }
        UserDetailsImpl ud = (UserDetailsImpl) auth.getPrincipal();
        Long me = ud.getUserAccount().getProfile().getId();
        if (!me.equals(userId)) {
            return "redirect:/" + me + "/dashboard";
        }

        YearMonth period = YearMonth.now();
        model.addAttribute("period", period);
        model.addAttribute("userId", userId);
        model.addAttribute("budgets", budgetService.summarize(userId, period));
        model.addAttribute("recommendations", recommendationService.generate(userId));
        return "dashboard";
    }
}
