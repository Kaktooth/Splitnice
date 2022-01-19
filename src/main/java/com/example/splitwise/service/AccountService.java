package com.example.splitwise.service;

import com.example.splitwise.model.User;
import com.example.splitwise.model.account.Account;

public interface AccountService extends EntityService<Account> {

    Account add(User user);

    Account getByUsername(String name);
}
