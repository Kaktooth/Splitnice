package com.example.splitwise.repository.expense;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseDto;
import com.example.splitwise.model.expense.ExpenseType;
import com.example.splitwise.model.expense.GroupExpense;
import com.example.splitwise.model.expense.IndividualExpense;
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
import java.util.List;
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
        String query = "INSERT INTO expense (amount, title, creation_date, currency_id, author_id) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setBigDecimal(1, expense.getAmount());
            ps.setString(2, expense.getTitle());
            ps.setTimestamp(3, timestamp);
            ps.setInt(4, DbCurrencyManager.getIdOfCurrencyType(expense.getCurrency()));
            ps.setInt(5, expense.getCreatorId());

            return ps;
        }, keyHolder);

        Integer entityId = (Integer) keyHolder.getKey();

        Expense.ExpenseBuilder expenseBuilder = new Expense.ExpenseBuilder()
            .withId(entityId)
            .withTitle(expense.getTitle())
            .withAmount(expense.getAmount())
            .withCreationDate(TimeConverter.convertTime(timestamp))
            .withCurrency(expense.getCurrency())
            .withCreatorId(expense.getCreatorId())
            .withSplittingType(expense.getSplittingType());

        if (expense instanceof GroupExpense) {
            return expenseBuilder
                .withTargetId(((GroupExpense) expense).getGroupId())
                .buildGroupExpense();
        } else if (expense instanceof IndividualExpense) {
            return expenseBuilder
                .withTargetId(((IndividualExpense) expense).getTargetId())
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

        return new Expense.ExpenseBuilder()
            .withId(savedExpense.getId())
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

        String query = "INSERT INTO individual_expense VALUES (?, ?)";

        jdbcTemplate.update(query, savedExpense.getId(), expense.getTargetId());

        return savedExpense;
    }

    @Override
    public List<Expense> getAllGroupExpenses(Set<Integer> ids) {
        return null;
    }

    @Override
    public List<Expense> getAllAccountExpenses(Set<Integer> ids) {
        return null;
    }

    @Override
    public List<Expense> getAccountExpenses(Integer accountId) {
//        String query = "SELECT account.id, account.username, users.username, amount, users.phone_number, user_id, currency_id FROM account " +
//            "INNER JOIN users ON users.id = user_id WHERE users.username = ?";
//        return jdbcTemplate.queryForList(query, Ex);
        return null;
    }
}
