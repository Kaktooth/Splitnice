package com.example.splitwise.service;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseDto;
import com.example.splitwise.model.transaction.Transaction;
import com.example.splitwise.model.transaction.TransactionBuilder;
import com.example.splitwise.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collection;
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
        Expense newExpense = expenseRepository.add(expense);

        switch (expense.getSplittingType()) {
            case EQUAL:
                for (Account account : expense.getAccounts()) {
                    if (!account.getId().equals(expense.getLander().getId())) {
                        Transaction transaction = new TransactionBuilder()
                            .withExpenseId(newExpense.getId())
                            .withAmount(getEqualShare(expense))
                            .withCurrency(expense.getCurrency())
                            .withLanderId(expense.getLander().getId())
                            .withReceiverId(account.getId())
                            .build();

                        transactionService.add(transaction);
                    }
                }
                return newExpense;
            case SPECIFIC:
                // TODO implement
                return newExpense;
        }
        return null;
    }

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
        transactionService.deleteTransactionsOfExpense(expenseId);
        expenseRepository.delete(expenseId);
    }

    private BigDecimal getEqualShare(ExpenseDto expense) {
        return expense.getAmount().divide(new BigDecimal(expense.getAccounts().size()), new MathContext(2));
    }
}
