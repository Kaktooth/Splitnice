package com.example.splitwise.model;

public class AccountGroupInfo implements Identifiable {

    private final Integer id;
    private final Integer roleId;
    private final Integer addedById;
    private final Integer groupId;
    private final Integer accountId;

    public AccountGroupInfo(Integer id, Integer roleId, Integer addedById, Integer groupId, Integer accountId) {
        this.id = id;
        this.roleId = roleId;
        this.addedById = addedById;
        this.groupId = groupId;
        this.accountId = accountId;
    }

    public Integer getRoleId() {
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
