package com.example.splitwise.repository.account;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.utils.DbCurrencyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
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
        String query = "INSERT INTO account (username, amount, currency_id, user_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, account.getUsername(), account.getMoneyAmount(), DbCurrencyManager.getIdOfCurrencyType(account.getCurrency()), account.getUserId());

        return account;
    }

    @Override
    public Account getById(Integer accountId) {
        String query = "SELECT account.id, account.username, users.username, amount, users.phone_number, user_id, currency_id " +
            "FROM account " +
            "INNER JOIN users ON users.id = user_id " +
            "WHERE account.id = ?";
        return jdbcTemplate.queryForObject(query, new AccountRowMapper(), accountId);
    }

    @Override
    public Account getByUsername(String username) {
        String query = "SELECT account.id, account.username, users.username, amount, users.phone_number, user_id, currency_id " +
            "FROM account " +
            "INNER JOIN users ON users.id = user_id " +
            "WHERE users.username = ?";
        return jdbcTemplate.queryForObject(query, new AccountRowMapper(), username);
    }

    @Override
    public void setMoneyAmount(Integer accountId, BigDecimal amount) {
        String queryForUsers = "UPDATE account SET amount = ? WHERE id = ?";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(queryForUsers);
            ps.setBigDecimal(1, amount);
            ps.setInt(2, accountId);
            return ps;
        });
    }

    @Override
    public List<Account> getAll(Set<Integer> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = String.format("SELECT * FROM expense WHERE id IN %s", inSql);

        return jdbcTemplate.queryForList(query, Account.class, ids);
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
            return new Account.AccountBuilder()
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
