package com.example.splitwise.model.account_group;

public final class AccountGroupInfoBuilder {

    private Integer id;
    private GroupRole roleId;
    private Integer addedById;
    private Integer groupId;
    private Integer accountId;

    public AccountGroupInfoBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public AccountGroupInfoBuilder withRoleId(GroupRole roleId) {
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
