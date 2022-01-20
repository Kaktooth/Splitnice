package com.example.splitwise.model.group;

import com.example.splitwise.model.expense.Expense;

import java.util.List;

public class GroupDto extends Group {

    List<Expense> expenses;

    public GroupDto(Integer id, String title, Integer creatorId, List<Expense> expenses) {
        super(id, title, creatorId);
        this.expenses = expenses;
    }

    public GroupDto(Group group, List<Expense> expenses) {
        super(group.getId(), group.getTitle(), group.getCreatorId());
        this.expenses = expenses;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}
