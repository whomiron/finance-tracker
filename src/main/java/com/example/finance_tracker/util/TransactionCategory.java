package com.example.finance_tracker.util;

import java.util.Arrays;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TransactionCategory {

    public final String HOUSING = "ЖИЛЬЕ";
    public final String UTILITIES = "КОМУНАЛЬНЫЕ ПЛАТЕЖИ";
    public final String GROCERIES = "ПРОДУКТЫ";
    public final String DINING = "ЕДА";
    public final String TRANSPORTATION = "ТРАНСПОРТ";
    public final String HEALTH = "ЗДОРОВЬЕ";
    public final String ENTERTAINMENT = "РАЗВЛЕЧЕНИЯ";
    public final String SAVINGS = "СБЕРЕЖЕНИЯ";
    public final String INVESTMENT = "ИНВЕСТИЦИИ";
    public final String OTHER = "ДРУГОЕ";

    public List<String> getAll() {
        return Arrays.asList(
                HOUSING,
                UTILITIES,
                GROCERIES,
                DINING,
                TRANSPORTATION,
                HEALTH,
                ENTERTAINMENT,
                SAVINGS,
                INVESTMENT,
                OTHER
        );
    }
}
