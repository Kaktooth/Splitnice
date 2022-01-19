package com.example.splitwise.service;

import com.example.splitwise.model.transaction.Transaction;
import com.example.splitwise.repository.transaction.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction add(Transaction transaction) {
        return transactionRepository.add(transaction);
    }

    @Override
    public Transaction getById(Integer transactionId) {
        return transactionRepository.getById(transactionId);
    }

    @Override
    public Collection<Transaction> getAll(Set<Integer> ids) {
        return transactionRepository.getAll(ids);
    }

    @Override
    public void delete(Integer transactionId) {
        transactionRepository.delete(transactionId);
    }

    @Override
    public void deleteTransactionsOfExpense(Integer expenseId) {

    }
}
