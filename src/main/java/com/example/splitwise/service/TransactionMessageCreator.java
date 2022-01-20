package com.example.splitwise.service;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.IndividualExpense;
import com.example.splitwise.model.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TransactionMessageCreator {

    private final TransactionService transactionService;

    private final UserService userService;

    @Autowired
    public TransactionMessageCreator(UserService userService,
                                     TransactionService transactionService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    public List<List<String>> getTransactionMessages(List<Expense> expenses) {
        List<List<String>> messages = new ArrayList<>();

        for (Expense expense : expenses) {
            List<Transaction> transactions = transactionService.getTransactionsFromExpense(
                Set.of(expense.getId())
            );

            List<String> userMessages = new ArrayList<>();
            String paidTransaction = userService.getById(expense.getCreatorId()).getEmail()
                + " paid " + expense.getAmountInfo() + " and owes "
                + transactions.get(0).getAmount().toString()
                + transactions.get(0).getCurrency().toString();

            String targetTransaction = userService.getById(
                ((IndividualExpense) expense).getTargetId()).getEmail()
                + " lent " + transactions.get(1).getAmount().toString()
                + transactions.get(1).getCurrency().toString();

            userMessages.add(paidTransaction);
            userMessages.add(targetTransaction);

            messages.add(userMessages);
        }

        return messages;
    }

    public List<List<Transaction>> getTransactions(List<Expense> expenses) {
        List<List<Transaction>> transactions = new ArrayList<>();

        for (Expense expense : expenses) {
            List<Transaction> expenseTransactions = transactionService.getTransactionsFromExpense(
                Set.of(expense.getId())
            );

            transactions.add(expenseTransactions);
        }

        return transactions;
    }
}
