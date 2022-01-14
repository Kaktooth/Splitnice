package com.example.splitwise.service;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseDto;
import com.example.splitwise.model.expense.GroupExpense;

import java.util.Collection;
import java.util.Set;

public interface ExpenseService extends EntityService<Expense> {

    Expense registerNewExpense(ExpenseDto expense);

    Collection<Expense> getAllGroupExpenses(Set<Integer> ids);

    Collection<Expense> getAllAccountExpenses(Set<Integer> ids);
}
