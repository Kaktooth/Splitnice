package com.example.splitwise.repository;

import com.example.splitwise.model.account.Account;

public interface AccountRepository extends EntityRepository<Account> {

    Account changeSignUpStatus(boolean signed);
}
