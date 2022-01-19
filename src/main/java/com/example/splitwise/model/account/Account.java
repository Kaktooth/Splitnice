package com.example.splitwise.model.account;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.Identifiable;

import java.math.BigDecimal;

public class Account implements Identifiable {

    private final Integer id;
    private final String username;
    private final String email;
    private final String phone;
    private final BigDecimal moneyAmount;
    private final Currency currency;

    public Account(Integer id, String username, String email, String phone, BigDecimal moneyAmount, Currency currency) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.moneyAmount = moneyAmount;
        this.currency = currency;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public Currency getCurrency() {
        return currency;
    }


    public static final class AccountBuilder {

        private Integer id;
        private String username;
        private String email;
        private String phone;
        private BigDecimal moneyAmount;
        private Currency currency;

        public AccountBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public AccountBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public AccountBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public AccountBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public AccountBuilder withMoneyAmount(BigDecimal moneyAmount) {
            this.moneyAmount = moneyAmount;
            return this;
        }

        public AccountBuilder withCurrency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public Account build() {
            return new Account(id, username, email, phone, moneyAmount, currency);
        }
    }
}
