package com.example.splitwise.service;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseDto;
import com.example.splitwise.model.transaction.Transaction;

import java.util.List;

public interface ExpenseService extends EntityService<Expense> {

    Expense registerNewExpense(ExpenseDto expense);
}
