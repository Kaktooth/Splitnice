package com.example.splitwise.repository;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.account.AccountBuilder;
import com.example.splitwise.service.UserService;
import com.example.splitwise.utils.DbCurrencyManager;
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

    private final UserService userService;

    @Autowired
    public AccountRepositoryImpl(JdbcTemplate jdbcTemplate, UserService userService) {
        this.userService = userService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account add(Account account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO account (username, amount, currency_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, account.getUsername());
            ps.setBigDecimal(2, account.getMoneyAmount());
            ps.setInt(3, DbCurrencyManager.getIdOfCurrencyType(account.getCurrency()));
            return ps;
        }, keyHolder);

        Integer accountId = (Integer) keyHolder.getKey();

        if (accountId != null) {
            return new AccountBuilder()
                .withId(accountId)
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
        String query = "SELECT account.id, account.username,users.username, amount, users.phone_number, user_id, currency_id\n" +
            "FROM account\n" +
            "INNER JOIN users ON users.id = user_id\n" +
            "WHERE account.id = ?";
        return jdbcTemplate.queryForObject(query, new AccountMapper(), accountId);
    }

    @Override
    public Account getByUsername(String username) {
        String query = "SELECT account.id, account.username, users.username, amount, users.phone_number, user_id, currency_id\n" +
            "FROM account\n" +
            "INNER JOIN users ON users.id = user_id\n" +
            "WHERE account.username = ?";
        return jdbcTemplate.queryForObject(query, new AccountMapper(), username);
    }

    @Override
    public Collection<Account> getAll(Set<Integer> ids) {
        return null;
    }

    @Override
    public void delete(Integer accountId) {

    }

    @Override
    public Account add(Account account, Integer userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO account (username, amount, currency_id, user_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, account.getUsername());
            ps.setBigDecimal(2, account.getMoneyAmount());
            ps.setInt(3, DbCurrencyManager.getIdOfCurrencyType(account.getCurrency()));
            ps.setInt(4, userId);
            return ps;
        }, keyHolder);

        Integer accountId = (Integer) keyHolder.getKey();

        if (accountId != null) {
        return new AccountBuilder()
            .withId(accountId)
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
}
