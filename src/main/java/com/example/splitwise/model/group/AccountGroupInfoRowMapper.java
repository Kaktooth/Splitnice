package com.example.splitwise.model.group;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.account.Account;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountGroupInfoRowMapper implements RowMapper<AccountGroupInfo> {

    @Override
    public AccountGroupInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("id");
        Integer addedById = rs.getInt("added_by_id");
        Integer groupId = rs.getInt("group_id");
        Integer accountId = rs.getInt("account_id");
        GroupRole role = GroupRole.valueOf(rs.getString("title"));
        String username = rs.getString("username");
        Integer userId = rs.getInt("user_id");
        BigDecimal amount = rs.getBigDecimal("amount");

        Account account = new Account(accountId, username, username, "", amount, Currency.USD, userId);

        return new AccountGroupInfo(id, role, addedById, groupId, account);
    }
}
