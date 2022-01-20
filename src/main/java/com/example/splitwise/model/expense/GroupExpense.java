package com.example.splitwise.model.expense;

import com.example.splitwise.model.Currency;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class GroupExpense extends Expense {

    private final Integer groupId;

    public GroupExpense(Integer id, String title, BigDecimal amount, OffsetDateTime creationDate, Currency currency, Integer creatorId, SplittingType splittingType, Integer groupId) {
        super(id, title, amount, creationDate, currency, creatorId, splittingType);
        this.groupId = groupId;
    }

    public Integer getGroupId() {
        return groupId;
    }
}
