package com.example.splitwise.repository.group;

import com.example.splitwise.model.group.Group;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupRowMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("group_id");
        String username = rs.getString("title");
        Integer creatorId = rs.getInt("creator_id");

        return new Group(id, username, creatorId);
    }
}
