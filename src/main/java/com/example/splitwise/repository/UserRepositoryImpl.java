package com.example.splitwise.repository;

import com.example.splitwise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

        String queryForAuthorities = "INSERT INTO authorities(id, username, authority) VALUES (?, ?, ?)";
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
        String query = "SELECT id, username, password, enabled, phone_number FROM users WHERE users.id = ?";
        User user = jdbcTemplate.queryForObject(query, new UserRowMapper(), entityId);
        return user;
    }

    @Override
    public Collection<User> getAll(Set<Integer> ids) {
        return null;
    }

    @Override
    public void delete(Integer entityId) {
        String query = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(query, entityId);
    }

    @Override
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        String query = "SELECT id, username, password, enabled, phone_number FROM users WHERE users.id = ?";
        User user = jdbcTemplate.queryForObject(query, new UserRowMapper(), userId);
        String salt = BCrypt.gensalt();
        String encryptedPassword = BCrypt.hashpw(oldPassword, salt);
        if (BCrypt.checkpw(encryptedPassword, user.getPassword())) {
            System.out.println("You cant change password");
            return;
        }

        String queryForUsers = "UPDATE users set password = ? WHERE users.id = ?";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(queryForUsers, new String[]{"id"});

            ps.setString(1, BCrypt.hashpw(newPassword, salt));
            ps.setInt(2, userId);
            return ps;
        });
    }

    @Override
    public void changeEmail(Integer userId, String email) {

    }

    @Override
    public void forgotPassword(Integer id) {

    }

    @Override
    public Integer getIdFromAuthenticationName(String name) {
        String query = "SELECT id, username, password, enabled, phone_number FROM users WHERE users.username = ?";
        User user = jdbcTemplate.queryForObject(query, new UserRowMapper(), name);

        return user.getId();
    }
}
