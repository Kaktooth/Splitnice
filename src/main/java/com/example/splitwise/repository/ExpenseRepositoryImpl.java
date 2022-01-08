package com.example.splitwise.repository;

import com.example.splitwise.model.Account;
import com.example.splitwise.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Set;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExpenseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Expense add(Expense expense) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO expense (amount, creation_date, currency_id, expense_type_id) VALUES (?,?,?,?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setBigDecimal(1, expense.getAmount());
            ps.setTimestamp(2, expense.getCreationDate());
            ps.setInt(3, expense.getCurrencyId());
            ps.setString(4, expense.getPhone());
            return ps;
        }, keyHolder);

        Integer entityId = (Integer) keyHolder.getKey();

        if (entityId != null) {
            return new Account(entityId, account.getUsername(), account.getEmail(),
                account.getPhone(), account.getMoneyAmount(), account.isSignedUp());
        } else {
            throw new RuntimeException("Account creation operation wasn't successful");
//        }
        return null;
    }

    @Override
    public Expense getById(Integer entityId) {
        return null;
    }

    @Override
    public Collection<Expense> getAll(Set<Integer> ids) {
        return null;
    }

    @Override
    public void delete(Integer entityId) {

    }
}
