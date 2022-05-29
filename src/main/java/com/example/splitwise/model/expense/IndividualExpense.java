package com.example.splitwise.model.expense;

import com.example.splitwise.model.Currency;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class IndividualExpense extends Expense {

    private final Integer targetId;

    public IndividualExpense(Integer id, String title, BigDecimal amount, OffsetDateTime creationDate, Currency currency,
                             Integer creatorId, SplittingType splittingType, Boolean paid, Integer targetId) {
        super(id, title, amount, creationDate, currency, creatorId, splittingType, paid);
        this.targetId = targetId;
    }

    public Integer getTargetId() {
        return targetId;
    }
}
