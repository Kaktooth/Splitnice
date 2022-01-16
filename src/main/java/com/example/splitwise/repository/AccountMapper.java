package com.example.splitwise.repository;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.User;
import com.example.splitwise.model.account.Account;
import com.example.splitwise.service.UserService;
import com.example.splitwise.utils.DbCurrencyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("id");
        String username = rs.getString("username");
        String phoneNumber = rs.getString("phone_number");
        String email = rs.getString("username");
        BigDecimal amount = rs.getBigDecimal("amount");
        Currency currency = DbCurrencyManager.getCurrencyTypeById(rs.getInt("currency_id"));

        return new Account(id, username, email, phoneNumber, amount, currency);
    }
}