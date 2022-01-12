package com.example.splitwise.model.expense;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.Identifiable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Expense implements Identifiable {

    private Integer id;
    private BigDecimal amount;
    private OffsetDateTime creationDate;
    private Currency currency;
    private ExpenseType type;

    public Expense() {
    }

    public Expense(Integer id, BigDecimal amount, OffsetDateTime creationDate, Currency currency, ExpenseType type) {
        this.id = id;
        this.amount = amount;
        this.creationDate = creationDate;
        this.currency = currency;
        this.type = type;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public ExpenseType getType() {
        return type;
    }
}
