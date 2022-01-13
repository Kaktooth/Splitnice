package com.example.splitwise.repository;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.GroupExpense;
import com.example.splitwise.model.expense.IndividualExpense;

public interface ExpenseRepository extends EntityRepository<Expense> {

    Expense addGroupExpense(GroupExpense expense);

    Expense addIndividualExpense(IndividualExpense expense);
}
