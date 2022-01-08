package com.example.splitwise.model.expense;

import com.example.splitwise.model.Currency;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public final class ExpenseBuilder {

    private Integer id;
    private BigDecimal amount;
    private OffsetDateTime creationDate;
    private Currency currency;
    private ExpenseType type;

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

    public ExpenseBuilder withType(ExpenseType type) {
        this.type = type;
        return this;
    }

    public Expense build() {
        return new Expense(id, amount, creationDate, currency, type);
    }
}
