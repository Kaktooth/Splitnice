package com.example.splitwise.model.expense;

import com.example.splitwise.model.account.Account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class ExpenseWrapperMap implements Serializable {

    private Map<Account, BigDecimal> shares;

    public ExpenseWrapperMap(Map<Account, BigDecimal> shares) {
        this.shares = shares;
    }

    public Map<Account, BigDecimal> getShares() {
        return shares;
    }
}
