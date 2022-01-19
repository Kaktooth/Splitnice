package com.example.splitwise.model.expense;

import com.example.splitwise.model.Currency;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class IndividualExpense extends Expense {

    private final Integer targetId;

    public IndividualExpense(Integer id, BigDecimal amount, OffsetDateTime creationDate, Currency currency, Integer creatorId, Integer targetId) {
        super(id, amount, creationDate, currency, creatorId);
        this.targetId = targetId;
    }

    public Integer getTargetId() {
        return targetId;
    }
}
