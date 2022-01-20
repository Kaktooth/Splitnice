package com.example.splitwise.service;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.repository.account.AccountRepository;
import com.example.splitwise.repository.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Account add(Account account) {

        return accountRepository.add(account);
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
    public Account getByUserEmail(String username) {
        return accountRepository.getByUsername(username);
    }

    @Override
    public void delete(Integer accountId) {
        accountRepository.delete(accountId);
    }
}
