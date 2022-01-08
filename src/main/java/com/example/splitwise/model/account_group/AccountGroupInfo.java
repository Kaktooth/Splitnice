package com.example.splitwise.model.account_group;

import com.example.splitwise.model.Identifiable;

public class AccountGroupInfo implements Identifiable {

    private final Integer id;
    private final GroupRole roleId;
    private final Integer addedById;
    private final Integer groupId;
    private final Integer accountId;

    public AccountGroupInfo(Integer id, GroupRole roleId, Integer addedById, Integer groupId, Integer accountId) {
        this.id = id;
        this.roleId = roleId;
        this.addedById = addedById;
        this.groupId = groupId;
        this.accountId = accountId;
    }

    public GroupRole getRoleId() {
        return roleId;
    }

    public Integer getAddedById() {
        return addedById;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    @Override
    public Integer getId() {
        return id;
    }
}
