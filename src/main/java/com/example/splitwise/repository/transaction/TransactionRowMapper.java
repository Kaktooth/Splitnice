package com.example.splitwise.repository.transaction;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.transaction.Transaction;
import com.example.splitwise.utils.DbCurrencyManager;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<Transaction> {

    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("id");
        BigDecimal amount = new BigDecimal(rs.getString("amount"));
        Currency currency = DbCurrencyManager.getCurrencyTypeById(rs.getInt("currency_id"));
        Integer landerId = rs.getInt("lander_id");
        Integer receiverId = rs.getInt("receiver_id");
        Integer expenseId = rs.getInt("expense_id");

        return new Transaction(id, amount, currency, landerId, receiverId, expenseId);
    }
}
