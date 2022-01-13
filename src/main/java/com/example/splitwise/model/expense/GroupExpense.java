package com.example.splitwise.model.expense;

import com.example.splitwise.model.Currency;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class GroupExpense extends Expense {

    private final Integer groupId;

    public GroupExpense(Integer id, BigDecimal amount, OffsetDateTime creationDate, Currency currency, Integer creatorId, Integer groupId) {
        super(id, amount, creationDate, currency, creatorId);
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }
}
