package com.example.splitwise.model.transaction;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.Identifiable;

import java.math.BigDecimal;

public class Transaction implements Identifiable {

    private final Integer id;
    private final BigDecimal amount;
    private final Currency currency;
    private final Integer landerId;
    private final Integer receiverId;
    private final Integer expenseId;

    public Transaction(Integer id, BigDecimal amount, Currency currency, Integer landerId, Integer receiverId, Integer expenseId) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.landerId = landerId;
        this.receiverId = receiverId;
        this.expenseId = expenseId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Integer getLanderId() {
        return landerId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public Integer getExpenseId() {
        return expenseId;
    }
}
