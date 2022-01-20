package com.example.splitwise.service;

import com.example.splitwise.model.transaction.Transaction;

import java.util.List;
import java.util.Set;

public interface TransactionService extends EntityService<Transaction> {

    void deleteTransactionsOfExpense(Integer expenseId);

    List<Transaction> getTransactionsFromExpense(Set<Integer> ids);
}
