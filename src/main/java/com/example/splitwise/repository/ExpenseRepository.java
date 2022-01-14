package com.example.splitwise.repository;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.GroupExpense;
import com.example.splitwise.model.expense.IndividualExpense;

import java.util.Collection;
import java.util.Set;

public interface ExpenseRepository extends EntityRepository<Expense> {

    Expense addGroupExpense(GroupExpense expense);

    Expense addIndividualExpense(IndividualExpense expense);

    Collection<Expense> getAllGroupExpenses(Set<Integer> ids);

    Collection<Expense> getAllAccountExpenses(Set<Integer> ids);
}
