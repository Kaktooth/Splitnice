package com.example.splitwise.repository.group;

import com.example.splitwise.model.group.Group;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class GroupRepositoryImpl implements GroupRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String queryForUserGroupList = "SELECT * FROM account\n" +
        "    INNER JOIN account_group ag on account.id = ag.account_id\n" +
        "    INNER JOIN \"group\" g on g.id = ag.group_id\n" +
        "    WHERE account_id = ?";

    private final String queryToAddGroup = "INSERT INTO \"group\" (title, creator_id) VALUES (?, ?)";
    private final String queryToGetById = "SELECT * FROM \"group\" WHERE \"group\".id = ?";
    private final String queryAddAccountGroup = "INSERT INTO account_group (role_id, added_by_id, group_id, account_id) VALUES (?, ?, ?, ?)";

    public GroupRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Group add(Group group) {
        jdbcTemplate.update(queryToAddGroup, group.getTitle(), group.getCreatorId());

        jdbcTemplate.update(queryAddAccountGroup, 1, group.getCreatorId(), group.getId(), group.getCreatorId());
        return group;
    }

    @Override
    public Group getById(Integer groupId) {
        return jdbcTemplate.queryForObject(queryToGetById, new GroupRowMapper(), groupId);
    }

    @Override
    public List<Group> getAll(Set<Integer> ids) {
        return null;
    }

    @Override
    public void delete(Integer entityId) {
    }

    @Override
    public List<Group> getAccountGroups(Integer accountId) {
        return jdbcTemplate.query(queryForUserGroupList, new GroupRowMapper(), accountId);
    }
}
