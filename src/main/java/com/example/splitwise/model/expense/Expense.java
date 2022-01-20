package com.example.splitwise.model.expense;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.Identifiable;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public abstract class Expense implements Identifiable {

    private final Integer id;
    private final String title;
    private final BigDecimal amount;
    private final OffsetDateTime creationDate;
    private final Currency currency;
    private final Integer creatorId;
    private final SplittingType splittingType;

    public Expense(Integer id, String title, BigDecimal amount, OffsetDateTime creationDate, Currency currency, Integer creatorId, SplittingType splittingType) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.creationDate = creationDate;
        this.currency = currency;
        this.creatorId = creatorId;
        this.splittingType = splittingType;
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

    public String getTitle() {
        return title;
    }

    public SplittingType getSplittingType() {
        return splittingType;
    }

    public String getAmountInfo() {
        return amount + " " + currency.toString();
    }

    public String getCreationInfo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM, dd, yyyy HH:mm:ss", Locale.US);
        return " at " + creationDate.format(formatter);
    }

    public static final class ExpenseBuilder {

        private Integer id;
        private String title;
        private BigDecimal amount;
        private OffsetDateTime creationDate;
        private Currency currency;
        private Integer creatorId;
        private Integer targetId;
        private SplittingType splittingType;

        public ExpenseBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public ExpenseBuilder withTitle(String title) {
            this.title = title;
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

        public ExpenseBuilder withSplittingType(SplittingType splittingType) {
            this.splittingType = splittingType;
            return this;
        }

        public ExpenseBuilder withTargetId(Integer targetId) {
            this.targetId = targetId;
            return this;
        }

        public GroupExpense buildGroupExpense() {
            return new GroupExpense(id, title, amount, creationDate, currency, creatorId, splittingType, targetId);
        }

        public IndividualExpense buildIndividualExpense() {
            return new IndividualExpense(id, title, amount, creationDate, currency, creatorId, splittingType, targetId);
        }
    }
}
