package com.example.splitwise.repository.group;

import com.example.splitwise.model.group.Group;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Set;

@Repository
public class GroupRepositoryImpl implements GroupRepository {

    private final JdbcTemplate jdbcTemplate;

    public GroupRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Group add(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO \"group\" (title, creator_id) VALUES (?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, group.getTitle());
            ps.setInt(2, group.getCreatorId());
            return ps;
        }, keyHolder);

        Integer groupId = (Integer) keyHolder.getKey();

        return new Group(groupId, group.getTitle(), group.getCreatorId());
    }

    @Override
    public Group getById(Integer entityId) {
        return null;
    }

    @Override
    public Collection<Group> getAll(Set<Integer> ids) {
        return null;
    }

    @Override
    public void delete(Integer entityId) {

    }
}
