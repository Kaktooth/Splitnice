package com.example.splitwise.repository;

import com.example.splitwise.model.account.Account;

public interface AccountRepository extends EntityRepository<Account> {

    Account add(Account account, Integer userId);

    Account getByUsername(String username);
}
