package com.example.splitwise.repository.user;

import com.example.splitwise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String newUserQuery = "INSERT INTO users(username, password, enabled, phone_number) VALUES (?, ?, ?, ?)";
    private final String queryForAuthorities = "INSERT INTO authorities(id, username, authority) VALUES (?, ?, ?)";
    private final String deleteUserQuery = "DELETE FROM users WHERE id = ?";
    private String query = "SELECT id, username, password, enabled, phone_number FROM users WHERE users.username = ?";

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User add(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(newUserQuery, new String[]{"id"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.isEnabled());
            ps.setString(4, user.getPhone());
            return ps;
        }, keyHolder);

        Integer entityId = (Integer) keyHolder.getKey();
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
    public List<User> getAll(Set<Integer> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = String.format("SELECT * FROM users WHERE id IN (%s)", inSql);

        List<User> users = jdbcTemplate.query(query, new UserRowMapper(), ids.toArray());
        return users;
    }

    @Override
    public void delete(Integer entityId) {
        jdbcTemplate.update(deleteUserQuery, entityId);
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

        String queryForUsers = "UPDATE users SET password = ? WHERE users.id = ?";

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
        User user = jdbcTemplate.queryForObject(query, new UserRowMapper(), name);

        return user.getId();
    }
}
