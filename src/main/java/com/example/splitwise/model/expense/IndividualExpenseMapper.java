package com.example.splitwise.model.expense;

import com.example.splitwise.model.Currency;
import com.example.splitwise.utils.DbCurrencyManager;
import com.example.splitwise.utils.TimeConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class IndividualExpenseMapper implements RowMapper<Expense> {

    @Override
    public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("id");
        BigDecimal amount = new BigDecimal(rs.getString("amount"));
        OffsetDateTime creationDate = TimeConverter.convertTime(rs.getTimestamp("creation_date"));
        Currency currency = DbCurrencyManager.getCurrencyTypeById(rs.getInt("currency_id"));
        Integer creatorId = rs.getInt("author_id");
        Integer targetId = rs.getInt("user_id");

        return new IndividualExpense(id, amount, creationDate, currency, creatorId, targetId);
    }

}
