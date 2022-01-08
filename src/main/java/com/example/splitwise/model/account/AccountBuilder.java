package com.example.splitwise.model.account;

import com.example.splitwise.model.Currency;

import java.math.BigDecimal;

public final class AccountBuilder {
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
