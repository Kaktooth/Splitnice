package com.example.splitwise.repository;

import com.example.splitwise.model.Account;

public interface AccountRepository extends EntityRepository<Account> {

    Account changeSignUpStatus(boolean signed);
}
