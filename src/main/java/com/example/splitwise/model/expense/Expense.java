package com.example.splitwise.model.expense;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.Identifiable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class Expense implements Identifiable {

    private final Integer id;
    private final BigDecimal amount;
    private final OffsetDateTime creationDate;
    private final Currency currency;
    private final Integer creatorId;

    public Expense(Integer id, BigDecimal amount,
                   OffsetDateTime creationDate,
                   Currency currency, Integer creatorId) {
        this.id = id;
        this.amount = amount;
        this.creationDate = creationDate;
        this.currency = currency;
        this.creatorId = creatorId;
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

    public Integer getCreatorId() {
        return creatorId;
    }

    public String getAmountInfo() {
        return amount + " " + currency;
    }

    public String getCreationInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM, dd, yyyy HH:mm:ss", Locale.US);
        return "Creation date: " + creationDate.format(formatter);
    }

    @Override
    public String toString() {
        return "Expense{" +
            "id=" + id +
            ", amount=" + amount +
            ", creationDate=" + creationDate +
            ", currency=" + currency +
            ", creatorId=" + creatorId +
            '}';
    }

    public static final class ExpenseBuilder {

        private Integer id;
        private BigDecimal amount;
        private OffsetDateTime creationDate;
        private Currency currency;
        private Integer creatorId;
        private Integer groupId;
        private Integer targetId;

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

        public ExpenseBuilder withCreatorId(Integer creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public ExpenseBuilder withGroupId(Integer id) {
            this.groupId = id;
            return this;
        }

        public ExpenseBuilder withTargetId(Integer id) {
            this.targetId = id;
            return this;
        }

        public GroupExpense buildGroupExpense() {
            return new GroupExpense(id, amount, creationDate, currency, creatorId, groupId);
        }

        public IndividualExpense buildIndividualExpense() {
            return new IndividualExpense(id, amount, creationDate, currency, creatorId, targetId);
        }
    }
}
