package com.example.splitwise.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class ExpenseBuilder {

    private BigDecimal amount;
    private LocalDateTime creationDate;
    private Integer landedById;
    private Integer landedToId;
    private Integer currencyId;

    public ExpenseBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public ExpenseBuilder withCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public ExpenseBuilder withLandedById(Integer landedById) {
        this.landedById = landedById;
        return this;
    }

    public ExpenseBuilder withLandedToId(Integer landedToId) {
        this.landedToId = landedToId;
        return this;
    }

    public ExpenseBuilder withCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    public Expense build() {
        return new Expense(amount, creationDate, landedById, landedToId, currencyId);
    }
}
