package com.example.splitwise.repository;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseBuilder;
import com.example.splitwise.utils.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
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
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO expense (amount, creation_date, currency_id, expense_type_id) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setBigDecimal(1, expense.getAmount());
            ps.setTimestamp(2, timestamp);
            ps.setInt(3, getCurrencyTypeId(expense.getCurrency().toString()));
            ps.setInt(4, getExpenseTypeId(expense.getType().toString()));

            return ps;
        }, keyHolder);

        Integer entityId = (Integer) keyHolder.getKey();

        return new ExpenseBuilder()
            .withId(entityId)
            .withAmount(expense.getAmount())
            .withCreationDate(TimeConverter.convertTime(timestamp))
            .withCurrency(expense.getCurrency())
            .withType(expense.getType())
            .build();
    }

    @Override
    public Expense getById(Integer expenseId) {
        String query = "SELECT * FROM expense WHERE id = ?";
        return jdbcTemplate.queryForObject(query, Expense.class, expenseId);
    }

    @Override
    public Collection<Expense> getAll(Set<Integer> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = String.format("SELECT * FROM expense WHERE id IN %s", inSql);

        return jdbcTemplate.queryForList(query, Expense.class, ids);
    }

    @Override
    public void delete(Integer expenseId) {
        String query = "DELETE FROM expense WHERE id = ?";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, expenseId);
            return ps;
        });
    }

    private Integer getExpenseTypeId(String title) {
        String query = "SELECT expense_type.id FROM expense_type WHERE title = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, title);
    }

    private Integer getCurrencyTypeId(String title) {
        String query = "SELECT currency.id FROM currency WHERE title = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, title);
    }
}
