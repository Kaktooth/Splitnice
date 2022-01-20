package com.example.splitwise.repository.account;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.repository.EntityRepository;

public interface AccountRepository extends EntityRepository<Account> {

    Account add(Account account, Integer userId);

    Account getByUsername(String username);
}
