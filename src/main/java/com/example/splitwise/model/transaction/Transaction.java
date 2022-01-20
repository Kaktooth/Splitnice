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

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + id +
            ", amount=" + amount +
            ", currency=" + currency +
            ", landerId=" + landerId +
            ", receiverId=" + receiverId +
            ", expenseId=" + expenseId +
            '}';
    }

    public static final class TransactionBuilder {

        private Integer id;
        private BigDecimal amount;
        private Currency currency;
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

        public TransactionBuilder withCurrency(Currency currency) {
            this.currency = currency;
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
            return new Transaction(id, amount, currency, landerId, receiverId, expenseId);
        }
    }
}
