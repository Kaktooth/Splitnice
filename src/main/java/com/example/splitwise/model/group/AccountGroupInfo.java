package com.example.splitwise.model.group;

import com.example.splitwise.model.Identifiable;
import com.example.splitwise.model.account.Account;

public class AccountGroupInfo implements Identifiable {

    private final Integer id;
    private final GroupRole role;
    private final Integer addedById;
    private final Integer groupId;
    private final Account account;

    public AccountGroupInfo(Integer id, GroupRole role, Integer addedById, Integer groupId, Account account) {
        this.id = id;
        this.role = role;
        this.addedById = addedById;
        this.groupId = groupId;
        this.account = account;
    }

    public GroupRole getRole() {
        return role;
    }

    public Integer getAddedById() {
        return addedById;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Account getAccount() {
        return account;
    }

    public String getInfo() {
        return " role: " + getRole().toString() + " balance: " + account.getMoneyAmount() + " " + account.getCurrency().toString();
    }

    @Override
    public Integer getId() {
        return id;
    }


    public static final class AccountGroupInfoBuilder {

        private Integer id;
        private GroupRole role;
        private Integer addedById;
        private Integer groupId;
        private Account account;

        public AccountGroupInfoBuilder withId(Integer id) {
            this.id = id;
            return this;
        }

        public AccountGroupInfoBuilder withRole(GroupRole role) {
            this.role = role;
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

        public AccountGroupInfoBuilder withAccountId(Account account) {
            this.account = account;
            return this;
        }

        public AccountGroupInfo build() {
            return new AccountGroupInfo(id, role, addedById, groupId, account);
        }
    }
}
