package com.example.finance_tracker.servicies;

import com.example.finance_tracker.dto.BudgetSummary;
import com.example.finance_tracker.dto.RecommendationResponse;
import com.example.finance_tracker.models.Goal;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    private final BudgetService budgetService;
    private final GoalService goalService;

    public RecommendationService(BudgetService budgetService, GoalService goalService) {
        this.budgetService = budgetService;
        this.goalService = goalService;
    }

    public RecommendationResponse generate(Long userId) {
        YearMonth cur = YearMonth.now();
        List<BudgetSummary> summaries = budgetService.summarize(userId, cur);
        List<Goal> goals = goalService.list(userId);

        List<String> highlights = new ArrayList<>();
        List<String> actions = new ArrayList<>();
        List<String> questions = new ArrayList<>();

        for (BudgetSummary summary : summaries) {
            BigDecimal denom = summary.getLimitAmount().max(BigDecimal.ONE);
            BigDecimal ratio = summary.getSpentAmount()
                    .divide(denom, 2, RoundingMode.HALF_UP);

            if (ratio.compareTo(BigDecimal.valueOf(1.1)) > 0) {
                highlights.add("Перерасход по категории " + summary.getCategory());
                actions.add("Сократить траты в " + summary.getCategory());
            } else if (ratio.compareTo(BigDecimal.valueOf(0.6)) < 0) {
                highlights.add("Недоиспользование бюджета: " + summary.getCategory());
                questions.add("Перераспределить бюджет из " + summary.getCategory() + "?");
            }

            if (summary.getForecastedEndOfMonth().compareTo(summary.getLimitAmount()) > 0) {
                actions.add("Превышение прогноза по " + summary.getCategory());
            }
        }

        for (Goal g : goals) {
            if (g.getCurrentAmount() == null || g.getTargetAmount() == null) continue;
            if (g.getCurrentAmount().compareTo(g.getTargetAmount().multiply(BigDecimal.valueOf(0.3))) < 0) {
                highlights.add("Цель \"" + g.getLabel() + "\" отстаёт");
                actions.add("Поставьте автоперевод для \"" + g.getLabel() + "\"");
            }
        }

        if (highlights.isEmpty()) highlights.add("Расходы соответствуют плану");
        if (actions.isEmpty()) actions.add("Продолжайте отслеживать расходы");

        return new RecommendationResponse(highlights, actions, questions);
    }
}
