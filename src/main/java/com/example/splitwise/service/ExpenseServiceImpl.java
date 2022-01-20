package com.example.splitwise.service;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseDto;
import com.example.splitwise.model.expense.IndividualExpense;
import com.example.splitwise.model.expense.SplittingType;
import com.example.splitwise.model.transaction.Transaction;
import com.example.splitwise.repository.expense.ExpenseRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final AccountService accountService;
    private final GroupService groupService;
    private final TransactionService transactionService;
    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(AccountService accountService,
                              GroupService groupService,
                              TransactionService transactionService,
                              ExpenseRepository expenseRepository) {
        this.accountService = accountService;
        this.groupService = groupService;
        this.transactionService = transactionService;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense add(Expense expense) {
        return expenseRepository.add(expense);
    }

    @Override
    public Expense addNewIndividualExpense(Expense expense, String targetEmail) {
        Integer targetId = getAccountId(targetEmail);

        IndividualExpense newIndividualExpense = new Expense.ExpenseBuilder()
            .withTitle(expense.getTitle())
            .withAmount(expense.getAmount())
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(expense.getCurrency())
            .withSplittingType(SplittingType.EQUAL)
            .withCreatorId(getAuthorId())
            .withTargetId(targetId)
            .buildIndividualExpense();

        Expense newExpense = expenseRepository.add(newIndividualExpense);
        BigDecimal equalShare = getEqualShare(newExpense, 2);

        Transaction landMoneyTransaction = new Transaction.TransactionBuilder()
            .withAmount(equalShare)
            .withCurrency(newExpense.getCurrency())
            .withLanderId(newExpense.getCreatorId())
            .withReceiverId(targetId)
            .withExpenseId(newExpense.getId())
            .build();

        Transaction receiveMoneyTransaction = new Transaction.TransactionBuilder()
            .withAmount(equalShare.negate())
            .withCurrency(newExpense.getCurrency())
            .withLanderId(newExpense.getCreatorId())
            .withReceiverId(targetId)
            .withExpenseId(newExpense.getId())
            .build();

        transactionService.add(landMoneyTransaction);
        transactionService.add(receiveMoneyTransaction);

        return newExpense;
    }

    @Override
    public Expense addNewGroupExpense(Expense expense, String targetTitle) {
        return null;
    }

    @Override
    public List<Expense> getAllGroupExpenses(Set<Integer> ids) {
        return expenseRepository.getAllGroupExpenses(ids);
    }

    @Override
    public List<Expense> getAllAccountExpenses(Set<Integer> ids) {
        List<Expense> accountExpenses = List.copyOf(expenseRepository.getAllAccountExpenses(ids));
        return accountExpenses;
    }

    @Override
    public List<Expense> getUserExpenses(String userEmail) {
        int accountId = accountService.getByUserEmail(userEmail).getId();
        return expenseRepository.getAccountExpenses(accountId);
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

    private BigDecimal getEqualShare(Expense expense, int count) {
        return expense.getAmount().divide(new BigDecimal(count), new MathContext(2));
    }

    private Integer getAuthorId() {
        Account author = accountService.getByUserEmail(
            SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        return author.getId();
    }

    private Integer getAccountId(String email) {
        return accountService.getByUserEmail(email).getId();
    }
}
