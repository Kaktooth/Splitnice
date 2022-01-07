package com.example.splitwise.repository;

import com.example.splitwise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User add(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String queryForUsers = "INSERT INTO users(username, password, enabled, phone_number) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(queryForUsers, new String[]{"id"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.isEnabled());
            ps.setString(4, user.getPhone());
            return ps;
        }, keyHolder);

        Integer entityId = (Integer) keyHolder.getKey();

        String queryForAuthorities = "INSERT INTO authorities(id,username, authority) VALUES (?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(queryForAuthorities);
            ps.setInt(1, entityId);
            ps.setString(2, user.getEmail());
            ps.setInt(3, 0);
            return ps;
        });

        if (entityId != null) {
            return new User(entityId, user.getEmail(), user.getPassword(),
                user.getPhone(), user.isEnabled());
        } else {
            throw new RuntimeException("User creation operation wasn't successful");
        }
    }

    @Override
    public User getById(Integer entityId) {
        return null;
    }

    @Override
    public Collection<User> getAll(Set<Integer> ids) {
        return null;
    }

    @Override
    public void delete(Integer entityId) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public void changeEmail(String phoneNumber) {

    }

    @Override
    public void forgotPassword(String phoneNumber) {

    }

    @Override
    public void changeSignInStatus(boolean signed) {

    }
}
