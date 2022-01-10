package com.example.splitwise.service;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseDto;
import com.example.splitwise.model.transaction.Transaction;
import com.example.splitwise.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final TransactionService transactionService;
    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(TransactionService transactionService, ExpenseRepository expenseRepository) {
        this.transactionService = transactionService;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense add(Expense expense) {
        return expenseRepository.add(expense);
    }

    @Override
    public Expense registerNewExpense(ExpenseDto expense) {
        return null;
    }

//    @Override
//    public Expense add(Expense expense, List<Transaction> transactions) {
//        for (Transaction transaction : transactions) {
//            transactionService.add(transaction);
//        }
//
//
//        return expenseRepository.add(expense);
//    }

    @Override
    public Expense getById(Integer expenseId) {
        return expenseRepository.getById(expenseId);
    }

    @Override
    public Collection<Expense> getAll(Set<Integer> ids) {
        return expenseRepository.getAll(ids);
    }

    @Override
    public void delete(Integer expenseId) {
        expenseRepository.delete(expenseId);
    }
}
