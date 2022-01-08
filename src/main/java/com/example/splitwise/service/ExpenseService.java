package com.example.splitwise.service;

import com.example.splitwise.model.Expense;
import com.example.splitwise.model.Transaction;

import java.util.List;

public interface ExpenseService extends EntityService<Expense> {

    public Expense add(Expense expense, List<Transaction> transactions);
}
