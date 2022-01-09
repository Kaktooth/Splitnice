package com.example.splitwise.service;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.transaction.Transaction;

import java.util.List;

public interface ExpenseService extends EntityService<Expense> {

    Expense add(Expense expense, List<Transaction> transactions);
}
