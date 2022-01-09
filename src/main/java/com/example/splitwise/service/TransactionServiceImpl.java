package com.example.splitwise.service;

import com.example.splitwise.model.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Override
    public Transaction add(Transaction entity) {
        return null;
    }

    @Override
    public Transaction getById(Integer entityId) {
        return null;
    }

    @Override
    public Collection<Transaction> getAll(Set<Integer> ids) {
        return null;
    }

    @Override
    public void delete(Integer entityId) {

    }
}
