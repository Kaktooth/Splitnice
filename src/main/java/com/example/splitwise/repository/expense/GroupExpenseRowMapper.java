package com.example.splitwise.repository.expense;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.GroupExpense;
import com.example.splitwise.model.expense.SplittingType;
import com.example.splitwise.utils.DbCurrencyManager;
import com.example.splitwise.utils.TimeConverter;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

public class GroupExpenseRowMapper implements RowMapper<Expense> {

    @Override
    public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("expense_id");
        String title = rs.getString("title");
        BigDecimal amount = new BigDecimal(rs.getString("amount"));
        OffsetDateTime creationDate = TimeConverter.convertTime(rs.getTimestamp("creation_date"));
        Currency currency = DbCurrencyManager.getCurrencyTypeById(rs.getInt("currency_id"));
        Integer creatorId = rs.getInt("author_id");
        Integer groupId = rs.getInt("group_id");
        Boolean paid = rs.getBoolean("paid");

        return new GroupExpense(id, title, amount, creationDate, currency, creatorId, SplittingType.EQUAL, paid, groupId);
    }
}
