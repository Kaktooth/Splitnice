package com.example.splitwise.service;

import com.example.splitwise.model.expense.Expense;

import java.util.List;
import java.util.Set;

public interface ExpenseService extends EntityService<Expense> {

    Expense addNewIndividualExpense(Expense expense, String targetTitle);

    Expense addNewGroupExpense(Expense expense, String targetTitle);

    List<Expense> getAllGroupExpenses(Set<Integer> ids);

    List<Expense> getAllAccountExpenses(Set<Integer> ids);

    List<Expense> getUserExpenses(String userEmail);

    List<Expense> getGroupExpenses(Integer groupId);

    void pay(Integer expenseId, Integer creatorId, Integer targetId);
}
