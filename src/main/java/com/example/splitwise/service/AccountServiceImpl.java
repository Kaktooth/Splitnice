package com.example.splitwise.service;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account add(Account account) {
        return accountRepository.add(account);
    }

    @Override
    public Account getById(Integer accountId) {
        return null;
    }

    @Override
    public Collection<Account> getAll(Set<Integer> ids) {
        return null;
    }

    @Override
    public Account changeSignUpStatus(boolean signed) {
        return null;
    }

    @Override
    public void delete(Integer accountId) {

    }
}
