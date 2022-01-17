package com.example.splitwise.repository;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseBuilder;
import com.example.splitwise.model.expense.GroupExpense;
import com.example.splitwise.model.expense.IndividualExpense;
import com.example.splitwise.model.expense.IndividualExpenseRowMapper;
import com.example.splitwise.utils.DbCurrencyManager;
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
        String query = "INSERT INTO expense (amount, creation_date, currency_id, author_id) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setBigDecimal(1, expense.getAmount());
            ps.setTimestamp(2, timestamp);
            ps.setInt(3, DbCurrencyManager.getIdOfCurrencyType(expense.getCurrency()));
            ps.setInt(4, expense.getCreatorId());

            return ps;
        }, keyHolder);

        Integer entityId = (Integer) keyHolder.getKey();

        if (expense instanceof GroupExpense) {
            return new ExpenseBuilder()
                .withId(entityId)
                .withAmount(expense.getAmount())
                .withCreationDate(TimeConverter.convertTime(timestamp))
                .withCurrency(expense.getCurrency())
                .withCreatorId(expense.getCreatorId())
                .buildGroupExpense();
        } else if (expense instanceof IndividualExpense) {
            return new ExpenseBuilder()
                .withId(entityId)
                .withAmount(expense.getAmount())
                .withCreationDate(TimeConverter.convertTime(timestamp))
                .withCurrency(expense.getCurrency())
                .buildIndividualExpense();
        }
        return null;
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

    @Override
    public Expense addGroupExpense(GroupExpense expense) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Expense savedExpense = add(expense);
        String query = "INSERT INTO group_expense VALUES (?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, savedExpense.getId());
            ps.setInt(2, expense.getGroupId());

            return ps;
        }, keyHolder);

        Integer groupExpenseId = (Integer) keyHolder.getKey();

        return new ExpenseBuilder()
            .withId(savedExpense.getId())
            .withAmount(expense.getAmount())
            .withCreationDate(savedExpense.getCreationDate())
            .withCurrency(expense.getCurrency())
            .withCreatorId(expense.getCreatorId())
            .withGroupId(expense.getGroupId())
            .buildIndividualExpense();
    }

    @Override
    public Expense addIndividualExpense(IndividualExpense expense) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Expense savedExpense = add(expense);
        String query = "INSERT INTO individual_expense VALUES (?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, savedExpense.getId());
            ps.setInt(2, expense.getTargetId());

            return ps;
        }, keyHolder);

        Integer groupExpenseId = (Integer) keyHolder.getKey();

        return new ExpenseBuilder()
            .withId(savedExpense.getId())
            .withAmount(expense.getAmount())
            .withCreationDate(savedExpense.getCreationDate())
            .withCurrency(expense.getCurrency())
            .withCreatorId(expense.getCreatorId())
            .withGroupId(expense.getTargetId())
            .buildIndividualExpense();
    }

    @Override
    public Collection<Expense> getAllGroupExpenses(Set<Integer> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = String.format("SELECT id, expense_id, group_id FROM group_expense WHERE id IN (%s)", inSql);

        return null; //jdbcTemplate.query(query, Expense.class, ids);
    }

    @Override
    public Collection<Expense> getAllAccountExpenses(Set<Integer> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = String.format("SELECT individual_expense.id, amount, creation_date, currency_id, author_id, user_id\n" +
            "FROM individual_expense\n" +
            "INNER JOIN expense ON expense.id = expense_id\n" +
            "INNER JOIN users ON users.id = user_id\n" +
            "WHERE individual_expense.id IN (%s)", inSql);

        return jdbcTemplate.query(query, new IndividualExpenseRowMapper(), ids.toArray());
    }
}
