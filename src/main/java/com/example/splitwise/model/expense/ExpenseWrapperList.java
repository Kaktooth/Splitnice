package com.example.splitwise.model.expense;

import com.example.splitwise.model.WrappedList;

import java.util.List;

public class ExpenseWrapperList implements WrappedList<Expense> {

    private List<Expense> expenses;

    public ExpenseWrapperList(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public List<Expense> getWrappedItems() {
        return expenses;
    }
}
