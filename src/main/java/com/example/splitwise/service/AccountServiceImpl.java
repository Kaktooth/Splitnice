package com.example.splitwise.service;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.User;
import com.example.splitwise.model.account.Account;
import com.example.splitwise.repository.account.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account add(User user) {

        Account account = new Account.AccountBuilder()
            .withUsername(user.getEmail())
            .withEmail(user.getEmail())
            .withPhone(user.getPhone())
            .withMoneyAmount(new BigDecimal(0))
            .withCurrency(Currency.USD)
            .build();

        return accountRepository.add(account, user.getId());
    }

    @Override
    public Account add(Account account) {
        Account newAccount = new Account.AccountBuilder()
            .withUsername(account.getEmail())
            .withEmail(account.getEmail())
            .withPhone(account.getPhone())
            .withMoneyAmount(new BigDecimal(0))
            .withCurrency(Currency.USD)
            .build();

        return accountRepository.add(newAccount);
    }

    @Override
    public Account getById(Integer accountId) {
        return accountRepository.getById(accountId);
    }

    @Override
    public Collection<Account> getAll(Set<Integer> ids) {
        return accountRepository.getAll(ids);
    }

    @Override
    public Account getByUsername(String username) {
        return accountRepository.getByUsername(username);
    }

    @Override
    public void delete(Integer accountId) {
        accountRepository.delete(accountId);
    }
}
