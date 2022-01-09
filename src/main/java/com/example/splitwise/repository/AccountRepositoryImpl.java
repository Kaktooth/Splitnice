package com.example.splitwise.repository;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.account.AccountBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Set;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account add(Account account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO account (username, amount, email, phone) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, account.getUsername());
            ps.setBigDecimal(2, account.getMoneyAmount());
            ps.setString(3, account.getEmail());
            ps.setString(4, account.getPhone());
            return ps;
        }, keyHolder);

        Integer entityId = (Integer) keyHolder.getKey();

        if (entityId != null) {
            return new AccountBuilder()
                .withId(entityId)
                .withUsername(account.getUsername())
                .withEmail(account.getEmail())
                .withPhone(account.getPhone())
                .withMoneyAmount(account.getMoneyAmount())
                .withCurrency(account.getCurrency())
                .build();
        } else {
            throw new RuntimeException("Account creation operation wasn't successful");
        }
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
