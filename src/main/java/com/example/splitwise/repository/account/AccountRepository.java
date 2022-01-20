package com.example.splitwise.repository.account;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.repository.EntityRepository;

import java.math.BigDecimal;

public interface AccountRepository extends EntityRepository<Account> {

    Account add(Account account, Integer userId);

    Account getByUsername(String username);

    void setMoneyAmount(Integer accountId, BigDecimal amount);
}
