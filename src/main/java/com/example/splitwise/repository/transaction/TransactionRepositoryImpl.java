package com.example.splitwise.repository.transaction;

import com.example.splitwise.model.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String addNewTransactionQuery = "INSERT INTO transaction (amount, currency_id, lander_id, receiver_id, expense_id) "
        + "VALUES (?, ?, ?, ?, ?)";
    private final String getByIdQuery = "SELECT * FROM transaction WHERE id = ?";
    private final String getCurrencyIdQuery = "SELECT currency.id FROM currency WHERE title = ?";
    private final String deleteTransactionByIdQuery = "DELETE FROM transaction WHERE id = ?";

    @Autowired
    public TransactionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transaction add(Transaction transaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(addNewTransactionQuery);
            ps.setBigDecimal(1, transaction.getAmount());
            ps.setInt(2, getCurrencyTypeId(transaction.getCurrency().toString()));
            ps.setInt(3, transaction.getLanderId());
            ps.setInt(4, transaction.getReceiverId());
            ps.setInt(5, transaction.getExpenseId());

            return ps;
        }, keyHolder);

        Integer transactionId = (Integer) keyHolder.getKey();

        return new Transaction.TransactionBuilder()
            .withId(transactionId)
            .withAmount(transaction.getAmount())
            .withLanderId(transaction.getLanderId())
            .withReceiverId(transaction.getReceiverId())
            .withExpenseId(transaction.getExpenseId())
            .build();
    }

    @Override
    public Transaction getById(Integer transactionId) {
        return jdbcTemplate.queryForObject(getByIdQuery, Transaction.class, transactionId);
    }

    @Override
    public List<Transaction> getAll(Set<Integer> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = String.format("SELECT * FROM transaction WHERE id IN %s", inSql);

        return jdbcTemplate.queryForList(query, Transaction.class, ids);
    }

    @Override
    public void delete(Integer transactionId) {
        jdbcTemplate.update(deleteTransactionByIdQuery, transactionId);
    }

    private Integer getCurrencyTypeId(String title) {
        return jdbcTemplate.queryForObject(getCurrencyIdQuery, Integer.class, title);
    }

    @Override
    public List<Transaction> getTransactionsFromExpense(Set<Integer> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = String.format("SELECT id, amount, currency_id, lander_id, receiver_id," +
            " expense_id FROM transaction WHERE expense_id IN (%s)", inSql);

        return jdbcTemplate.query(query, new TransactionRowMapper(), ids.toArray());
    }
}
