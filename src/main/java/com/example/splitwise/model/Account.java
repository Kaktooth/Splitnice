package com.example.splitwise.model;

import java.math.BigDecimal;

public class Account extends BusinessEntity {

    private final String username;

    private final String email;

    private final String phone;

    private final BigDecimal moneyAmount;

    public Account(Integer id, String username, String email, String phone, BigDecimal moneyAmount) {
        super(id);
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.moneyAmount = moneyAmount;
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
}
