package com.example.finance_tracker.servicies;

import com.example.finance_tracker.dto.BudgetSummary;
import com.example.finance_tracker.models.Transaction;
import com.example.finance_tracker.repositories.BudgetRepository;
import com.example.finance_tracker.repositories.TransactionRepository;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;

    public BudgetService(BudgetRepository budgetRepository,
                         TransactionRepository transactionRepository) {
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<BudgetSummary> summarize(Long userId, YearMonth period) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime start = period.atDay(1).atStartOfDay().atOffset(now.getOffset());
        OffsetDateTime end = period.atEndOfMonth().atTime(23, 59).atOffset(now.getOffset());

        List<Transaction> transactions = transactionRepository
                .findByAccountOwnerIdAndOccurredAtBetween(userId, start, end);

        return budgetRepository.findByOwnerIdAndPeriod(userId, period)
                .stream()
                .sorted(Comparator.comparing(b -> b.getCategory() != null ? b.getCategory() : ""))
                .map(budget -> {
                    BigDecimal spent = transactions.stream()
                            .filter(tx -> tx.getCategory() != null && tx.getCategory().equals(budget.getCategory()))
                            .map(Transaction::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal remaining = budget.getLimitAmount().subtract(spent);

                    SimpleRegression regression = new SimpleRegression(true);
                    transactions.stream()
                            .filter(tx -> tx.getCategory() != null && tx.getCategory().equals(budget.getCategory()))
                            .sorted(Comparator.comparing(Transaction::getOccurredAt))
                            .forEachOrdered(tx -> regression.addData(
                                    tx.getOccurredAt().getDayOfMonth(),
                                    tx.getAmount().doubleValue()));

                    double slope = regression.getSlope();
                    int daysLeft = Math.max(period.lengthOfMonth() - now.getDayOfMonth(), 0);
                    double forecast = slope >= 0
                            ? spent.doubleValue() + slope * daysLeft
                            : spent.doubleValue();

                    return new BudgetSummary(
                            budget.getCategory(),
                            budget.getLimitAmount(),
                            spent,
                            remaining,
                            BigDecimal.valueOf(Math.max(forecast, 0)).setScale(2, RoundingMode.HALF_UP)
                    );
                })
                .collect(Collectors.toList());
    }
}
