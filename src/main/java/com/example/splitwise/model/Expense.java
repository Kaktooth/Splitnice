package com.example.splitwise.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Expense {

    private final BigDecimal amount;
    private final LocalDateTime creationDate;
    private final Integer landedById;
    private final Integer landedToId;
    private final Integer currencyId;

    public Expense(BigDecimal amount,
                   LocalDateTime creationDate,
                   Integer landedById,
                   Integer landedToId,
                   Integer currencyId) {
        this.amount = amount;
        this.creationDate = creationDate;
        this.landedById = landedById;
        this.landedToId = landedToId;
        this.currencyId = currencyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getLandedById() {
        return landedById;
    }

    public Integer getLandedToId() {
        return landedToId;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }
}
