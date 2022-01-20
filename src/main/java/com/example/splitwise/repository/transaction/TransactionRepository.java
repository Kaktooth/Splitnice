package com.example.splitwise.repository.transaction;

import com.example.splitwise.model.transaction.Transaction;
import com.example.splitwise.repository.EntityRepository;

import java.util.List;
import java.util.Set;

public interface TransactionRepository extends EntityRepository<Transaction> {
    List<Transaction> getTransactionsFromExpense(Set<Integer> ids);
}
