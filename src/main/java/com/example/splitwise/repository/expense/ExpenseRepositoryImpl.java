package com.example.splitwise.repository.expense;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.GroupExpense;
import com.example.splitwise.model.expense.IndividualExpense;
import com.example.splitwise.utils.DbCurrencyManager;
import com.example.splitwise.utils.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {

    private final JdbcTemplate jdbcTemplate;

    String newExpenseQuery = "INSERT INTO expense (amount, title, creation_date, currency_id, author_id, paid) VALUES (?, ?, ?, ?, ?, ?)";
    String individualExpenseQuery = "INSERT INTO individual_expense (expense_id, user_id) VALUES (?, ?)";
    String groupExpenseQuery = "INSERT INTO group_expense (expense_id, group_id) VALUES (?, ?)";

    String getAccountExpensesQuery = "SELECT * FROM individual_expense\n" +
        "INNER JOIN expense ON expense.id = expense_id\n" +
        "INNER JOIN users ON users.id = user_id\n" +
        "WHERE user_id = ?  OR author_id = ?";

    String getGroupExpensesQuery = "SELECT * " +
        "FROM group_expense\n" +
        "INNER JOIN expense ON expense.id = expense_id\n" +
        "WHERE group_id = ? ";

    String setPaidValue = "UPDATE expense SET paid = true WHERE id = ?";

    @Autowired
    public ExpenseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Expense add(Expense expense) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(newExpenseQuery, new String[]{"id"});
            ps.setBigDecimal(1, expense.getAmount());
            ps.setString(2, expense.getTitle());
            ps.setTimestamp(3, timestamp);
            ps.setInt(4, DbCurrencyManager.getIdOfCurrencyType(expense.getCurrency()));
            ps.setInt(5, expense.getCreatorId());
            ps.setBoolean(6, false);

            return ps;
        }, keyHolder);

        Integer entityId = (Integer) keyHolder.getKey();

        Expense.ExpenseBuilder expenseBuilder = new Expense.ExpenseBuilder()
            .withId(entityId)
            .withPaid(expense.getPaid())
            .withTitle(expense.getTitle())
            .withAmount(expense.getAmount())
            .withCreationDate(TimeConverter.convertTime(timestamp))
            .withCurrency(expense.getCurrency())
            .withCreatorId(expense.getCreatorId())
            .withSplittingType(expense.getSplittingType());

        if (expense instanceof GroupExpense) {
            Integer groupId = ((GroupExpense) expense).getGroupId();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(groupExpenseQuery, new String[]{"id"});
                ps.setInt(1, entityId);
                ps.setInt(2, groupId);
                return ps;
            }, keyHolder);

            return expenseBuilder
                .withGroupId(((GroupExpense) expense).getGroupId())
                .buildGroupExpense();

        } else if (expense instanceof IndividualExpense) {
            Integer targetId = ((IndividualExpense) expense).getTargetId();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(individualExpenseQuery, new String[]{"id"});
                ps.setInt(1, entityId);
                ps.setInt(2, targetId);
                return ps;
            }, keyHolder);
            return expenseBuilder
                .withTargetId(((IndividualExpense) expense).getTargetId())
                .buildIndividualExpense();
        }
        return null;
    }

    @Override
    public Expense getById(Integer expenseId) {
        String query = "SELECT * FROM expense, individual_expense WHERE expense.id = ? AND expense_id = ?";
        String query2 = "SELECT * FROM expense, group_expense WHERE expense.id = ? AND expense_id = ?";
        Expense expense;
        try {
            expense = jdbcTemplate.queryForObject(query, new IndividualExpenseRowMapper(), expenseId, expenseId);
        } catch (EmptyResultDataAccessException exception) {

            expense = jdbcTemplate.queryForObject(query2, new GroupExpenseRowMapper(), expenseId, expenseId);
        }
        return expense;
    }

    @Override
    public List<Expense> getAll(Set<Integer> ids) {
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

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(groupExpenseQuery);
            ps.setInt(1, savedExpense.getId());
            ps.setInt(2, expense.getGroupId());

            return ps;
        }, keyHolder);

        Integer groupExpenseId = (Integer) keyHolder.getKey();

        return new Expense.ExpenseBuilder()
            .withId(savedExpense.getId())
            .withPaid(savedExpense.getPaid())
            .withAmount(expense.getAmount())
            .withCreationDate(savedExpense.getCreationDate())
            .withCurrency(expense.getCurrency())
            .withCreatorId(expense.getCreatorId())
            .withTargetId(expense.getGroupId())
            .buildIndividualExpense();
    }

    @Override
    public Expense addIndividualExpense(IndividualExpense expense) {
        Expense savedExpense = add(expense);

        jdbcTemplate.update(individualExpenseQuery, savedExpense.getId(), expense.getTargetId());

        return savedExpense;
    }

    @Override
    public List<Expense> getAllGroupExpenses(Set<Integer> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = String.format("SELECT group_expense.id, amount, creation_date, currency_id, author_id, group_id\n" +
            "FROM group_expense\n" +
            "INNER JOIN expense ON expense.id = expense_id\n" +
            "INNER JOIN users ON users.id = user_id\n" +
            "WHERE group_expense.id IN (%s)", inSql);

        return jdbcTemplate.query(query, new GroupExpenseRowMapper(), ids.toArray());
    }

    @Override
    public List<Expense> getAllAccountExpenses(Set<Integer> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = String.format("SELECT individual_expense.id, amount, creation_date, currency_id, user_id, title, author_id\n" +
            "FROM individual_expense\n" +
            "INNER JOIN expense ON expense.id = expense_id\n" +
            "INNER JOIN users ON users.id = user_id\n" +
            "WHERE user_id IN (%s) OR author_id IN (%s)", inSql);

        return jdbcTemplate.query(query, new IndividualExpenseRowMapper(), ids.toArray());
    }

    @Override
    public List<Expense> getAccountExpenses(Integer accountId) {
        return jdbcTemplate.query(getAccountExpensesQuery, new IndividualExpenseRowMapper(), accountId, accountId);
    }

    @Override
    public List<Expense> getGroupExpenses(Integer groupId) {
        return jdbcTemplate.query(getGroupExpensesQuery, new GroupExpenseRowMapper(), groupId);
    }

    @Override
    public void pay(Integer id) {
        jdbcTemplate.update(setPaidValue, id);
    }
}
