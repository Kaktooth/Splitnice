package com.example.splitwise.repository.expense;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.GroupExpense;
import com.example.splitwise.model.expense.IndividualExpense;
import com.example.splitwise.repository.EntityRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ExpenseRepository extends EntityRepository<Expense> {

    Expense addGroupExpense(GroupExpense expense);

    Expense addIndividualExpense(IndividualExpense expense);

    List<Expense> getAllGroupExpenses(Set<Integer> ids);

    List<Expense> getAllAccountExpenses(Set<Integer> ids);
}
