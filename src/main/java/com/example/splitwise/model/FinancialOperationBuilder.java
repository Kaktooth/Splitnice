package com.example.splitwise.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class FinancialOperationBuilder {

    private Integer id;
    private BigDecimal amount;
    private LocalDateTime creationDate;
    private Integer landedById;
    private Integer landedToId;
    private Integer currencyId;

    public FinancialOperationBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public FinancialOperationBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public FinancialOperationBuilder withCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public FinancialOperationBuilder withLandedById(Integer landedById) {
        this.landedById = landedById;
        return this;
    }

    public FinancialOperationBuilder withLandedToId(Integer landedToId) {
        this.landedToId = landedToId;
        return this;
    }

    public FinancialOperationBuilder withCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    public FinancialOperation build() {
        return new FinancialOperation(id, amount, creationDate, landedById, landedToId, currencyId);
    }
}
