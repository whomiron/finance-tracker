package com.example.finance_tracker.controllers;

import com.example.finance_tracker.dto.BudgetSummary;
import com.example.finance_tracker.servicies.BudgetService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@Controller
@RequestMapping("/{userId}/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping("/summary")
    public String summarize(@PathVariable Long userId,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth period,
                            Model model) {
        YearMonth targetPeriod = period != null ? period : YearMonth.now();
        List<BudgetSummary> summaries = budgetService.summarize(userId, targetPeriod);
        model.addAttribute("period", targetPeriod);
        model.addAttribute("summaries", summaries);
        model.addAttribute("userId", userId);
        return "budgets";
    }
}
