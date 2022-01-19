package com.example.splitwise.service;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseDto;

import java.util.List;
import java.util.Set;

public interface ExpenseService extends EntityService<Expense> {

    Expense registerNewExpense(ExpenseDto expense);

    List<Expense> getAllGroupExpenses(Set<Integer> ids);

    List<Expense> getAllAccountExpenses(Set<Integer> ids);
}
