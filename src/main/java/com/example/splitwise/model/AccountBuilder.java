package com.example.splitwise.model;

import java.math.BigDecimal;

public final class AccountBuilder {

    private String username;
    private String email;
    private String phone;
    private BigDecimal moneyAmount;
    private Integer id;

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

    public AccountBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public Account build() {
        return new Account(id, username, email, phone, moneyAmount);
    }
}
