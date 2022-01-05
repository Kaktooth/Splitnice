package com.example.splitwise.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FinancialOperation implements Identifiable {

    private final Integer id;
    private final BigDecimal amount;
    private final LocalDateTime creationDate;
    private final Integer landedById;
    private final Integer landedToId;
    private final Integer currencyId;

    public FinancialOperation(Integer id,
                              BigDecimal amount,
                              LocalDateTime creationDate,
                              Integer landedById,
                              Integer landedToId,
                              Integer currencyId) {
        this.id = id;
        this.amount = amount;
        this.creationDate = creationDate;
        this.landedById = landedById;
        this.landedToId = landedToId;
        this.currencyId = currencyId;
    }

    @Override
    public Integer getId() {
        return id;
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
