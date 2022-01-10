package com.example.splitwise.model;

public final class AccountGroupInfoBuilder {

    private Integer id;
    private Integer roleId;
    private Integer addedById;
    private Integer groupId;
    private Integer accountId;

    public AccountGroupInfoBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public AccountGroupInfoBuilder withRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    public AccountGroupInfoBuilder withAddedById(Integer addedById) {
        this.addedById = addedById;
        return this;
    }

    public AccountGroupInfoBuilder withGroupId(Integer groupId) {
        this.groupId = groupId;
        return this;
    }

    public AccountGroupInfoBuilder withAccountId(Integer accountId) {
        this.accountId = accountId;
        return this;
    }

    public AccountGroupInfo build() {
        return new AccountGroupInfo(id, roleId, addedById, groupId, accountId);
    }
}
