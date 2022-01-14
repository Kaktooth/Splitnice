package com.example.splitwise.repository;

import com.example.splitwise.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        boolean enabled = rs.getBoolean("enabled");
        String phoneNumber = rs.getString("phone_number");

        return new User(id, username, phoneNumber, password, enabled);
    }
}