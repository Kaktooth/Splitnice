package com.example.splitwise.repository.group;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GroupAccountsRowMapper implements RowMapper<Map<String, String>> {

    Map<String, String> accounts = new HashMap<>();

    @Override
    public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {

        String role = rs.getString("title");
        String creator = rs.getString("creator");
        accounts.put(creator, role);

        return accounts;
    }
}