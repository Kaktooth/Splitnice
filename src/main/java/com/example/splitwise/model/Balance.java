package com.example.splitwise.model;

import java.math.BigDecimal;

public class Balance {

    private final BigDecimal amount;
    private final Integer balanceCurrencyId;
    private final Integer ownerId;

    public Balance(BigDecimal amount, Integer balanceCurrencyId, Integer ownerId) {
        this.amount = amount;
        this.balanceCurrencyId = balanceCurrencyId;
        this.ownerId = ownerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getBalanceCurrencyId() {
        return balanceCurrencyId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }
}
