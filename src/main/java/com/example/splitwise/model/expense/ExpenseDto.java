package com.example.splitwise.model.expense;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.account.Account;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class ExpenseDto extends Expense {

    private final SplittingType splittingType;
    private final List<Account> accounts;

    public ExpenseDto(Integer id, BigDecimal amount, OffsetDateTime creationDate, Currency currency, ExpenseType type, SplittingType splittingType, List<Account> accounts) {
        super(id, amount, creationDate, currency, type);
        this.splittingType = splittingType;
        this.accounts = accounts;
    }

    public SplittingType getSplittingType() {
        return splittingType;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
