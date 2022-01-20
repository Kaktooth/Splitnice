package com.example.splitwise.service;

import com.example.splitwise.model.account.Account;

public interface AccountService extends EntityService<Account> {

    Account getByUserEmail(String name);
}
