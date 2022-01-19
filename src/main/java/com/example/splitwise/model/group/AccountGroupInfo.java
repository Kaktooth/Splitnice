package com.example.splitwise.model.group;

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


    public static final class AccountGroupInfoBuilder {

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
}
