package com.example.splitwise.model.group;

import com.example.splitwise.model.expense.Expense;

import java.util.List;

public class GroupDto extends Group {

    List<Expense> expenses;
    List<AccountGroupInfo> accounts;

    public GroupDto(Integer id, String title, Integer creatorId, List<Expense> expenses, List<AccountGroupInfo> accounts) {
        super(id, title, creatorId);
        this.expenses = expenses;
        this.accounts = accounts;
    }

    public GroupDto(Group group, List<Expense> expenses, List<AccountGroupInfo> accounts) {
        super(group.getId(), group.getTitle(), group.getCreatorId());
        this.expenses = expenses;
        this.accounts = accounts;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public List<AccountGroupInfo> getAccounts() {
        return accounts;
    }
}
