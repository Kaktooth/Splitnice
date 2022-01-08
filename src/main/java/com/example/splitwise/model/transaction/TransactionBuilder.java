package com.example.splitwise.model.transaction;

import java.math.BigDecimal;

public final class TransactionBuilder {
    private Integer id;
    private BigDecimal amount;
    private Integer landerId;
    private Integer receiverId;
    private Integer expenseId;

    public TransactionBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public TransactionBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public TransactionBuilder withLanderId(Integer landerId) {
        this.landerId = landerId;
        return this;
    }

    public TransactionBuilder withReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public TransactionBuilder withExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
        return this;
    }

    public Transaction build() {
        return new Transaction(id, amount, landerId, receiverId, expenseId);
    }
}
