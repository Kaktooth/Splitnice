package com.example.splitwise.model;

import java.math.BigDecimal;

public class Account implements Identifiable {

    private final Integer id;
    private final String username;
    private final String email;
    private final String phone;
    private final BigDecimal moneyAmount;
    private final boolean signedUp;

    public Account(Integer id, String username, String email, String phone, BigDecimal moneyAmount, boolean signedUp) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.moneyAmount = moneyAmount;
        this.signedUp = signedUp;
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

    public boolean isSignedUp() {
        return signedUp;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
