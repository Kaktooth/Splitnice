package com.example.splitwise.utils;

import com.example.splitwise.model.Currency;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbCurrencyManager {

    private static JdbcTemplate jdbcTemplate;

    public DbCurrencyManager(JdbcTemplate jdbcTemplate) {
        DbCurrencyManager.jdbcTemplate = jdbcTemplate;
    }

    public static Integer getIdOfCurrencyType(Currency currency) {
        String query = "SELECT currency.id FROM currency WHERE title = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, currency.name());
    }

    public static Currency getCurrencyTypeById(Integer currencyId) {
        String query = "SELECT currency.title FROM currency WHERE id = ?";
        String currencyTitle = jdbcTemplate.queryForObject(query, String.class, currencyId);
        return Currency.valueOf(currencyTitle);
    }
}
