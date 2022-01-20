package com.example.splitwise.service;

import com.example.splitwise.model.account.Account;

import java.math.BigDecimal;

public interface AccountService extends EntityService<Account> {

    Account getByUserEmail(String name);

    void setMoneyAmount(Integer accountId, BigDecimal amount);
}
