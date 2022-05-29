package com.example.splitwise.repository.group;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.group.AccountGroupInfo;
import com.example.splitwise.model.group.AccountGroupInfoRowMapper;
import com.example.splitwise.model.group.Group;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Set;

@Repository
public class GroupRepositoryImpl implements GroupRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String queryForUserGroupList = "SELECT * FROM account\n" +
        "    INNER JOIN account_group ag on account.id = ag.account_id\n" +
        "    INNER JOIN \"group\" g on g.id = ag.group_id\n" +
        "    WHERE account_id = ? ";

    private final String getAllAccounts = "SELECT * FROM account_group\n" +
        "        INNER JOIN group_role ON role_id = group_role.id\n" +
        "        INNER JOIN account ON account_id = account.id\n" +
        "        INNER JOIN users ON user_id = users.id\n" +
        "        WHERE group_id = ? ";

    private final String queryToAddGroup = "INSERT INTO \"group\" (title, creator_id) VALUES (?, ?)";
    private final String queryToGetById = "SELECT * FROM \"group\" WHERE \"group\".id = ?";
    private final String queryToGetByTitle = "SELECT * FROM \"group\" WHERE \"group\".title = ?";
    private final String queryAddAccountGroup = "INSERT INTO account_group (role_id, added_by_id, group_id, account_id) VALUES (?, ?, ?, ?)";

    private final String deleteAccounts = "DELETE FROM account_group WHERE group_id = ?";
    private final String deleteGroup = "DELETE FROM \"group\" WHERE id = ?";

    public GroupRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Group add(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(queryToAddGroup, new String[]{"id"});
            ps.setString(1, group.getTitle());
            ps.setInt(2, group.getCreatorId());

            return ps;
        }, keyHolder);

        Integer entityId = (Integer) keyHolder.getKey();

        jdbcTemplate.update(queryAddAccountGroup, 1, group.getCreatorId(), entityId, group.getCreatorId());
        return group;
    }

    @Override
    public Group getById(Integer groupId) {
        return jdbcTemplate.queryForObject(queryToGetById, new SingleGroupRowMapper(), groupId);
    }

    @Override
    public List<Group> getAll(Set<Integer> ids) {
        return null;
    }

    @Override
    public void delete(Integer entityId) {
        jdbcTemplate.update(deleteAccounts, entityId);
        jdbcTemplate.update(deleteGroup, entityId);
    }

    @Override
    public List<Group> getAccountGroups(Integer accountId) {
        return jdbcTemplate.query(queryForUserGroupList, new GroupRowMapper(), accountId);
    }

    @Override
    public Group getByTitle(String title) {
        return jdbcTemplate.queryForObject(queryToGetByTitle, new SingleGroupRowMapper(), title);
    }

    @Override
    public List<AccountGroupInfo> getAccounts(Integer groupId) {
        return jdbcTemplate.query(getAllAccounts, new AccountGroupInfoRowMapper(), groupId);
    }

    @Override
    public void addAccount(Account account, Group group) {
        jdbcTemplate.update(queryAddAccountGroup, 2, group.getCreatorId(), group.getId(), account.getId());
    }
}
