package com.example.splitwise.model.expense;

import com.example.splitwise.model.Currency;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public final class ExpenseBuilder {

    private Integer id;
    private BigDecimal amount;
    private OffsetDateTime creationDate;
    private Currency currency;
    private Integer creatorId;
    private Integer groupId;
    private Integer accountId;

    public ExpenseBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public ExpenseBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public ExpenseBuilder withCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public ExpenseBuilder withCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public ExpenseBuilder withCreatorId(Integer id) {
        this.creatorId = id;
        return this;
    }

    public ExpenseBuilder withGroupId(Integer id) {
        this.groupId = id;
        return this;
    }

    public ExpenseBuilder withAccountId(Integer id) {
        this.accountId = id;
        return this;
    }

    public GroupExpense buildGroupExpense() {
        return new GroupExpense(id, amount, creationDate, currency, creatorId, groupId);
    }

    public IndividualExpense buildIndividualExpense() {
        return new IndividualExpense(id, amount, creationDate, currency, creatorId, accountId);
    }
}
