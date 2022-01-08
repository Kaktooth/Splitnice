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
}
