package com.example.splitwise.model.expense;

import java.util.List;

public class ExpenseWrapperList {

    private List<Expense> expenses;

    public ExpenseWrapperList(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}
