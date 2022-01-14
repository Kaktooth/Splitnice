package com.example.splitwise.model.expense;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.WrappedList;
import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.account.AccountWrapperList;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class ExpenseDto extends Expense {

    private final ExpenseType type;
    private final SplittingType splittingType;
    private final Account lander;
    private final AccountWrapperList accounts;
    private final ExpenseWrapperMap shares;

    public ExpenseDto(Integer id,
                      BigDecimal amount,
                      OffsetDateTime creationDate,
                      Currency currency,
                      ExpenseType type,
                      SplittingType splittingType,
                      Account lander,
                      AccountWrapperList accounts,
                      ExpenseWrapperMap shares,
                      Integer creatorId) {
        super(id, amount, creationDate, currency, creatorId);
        this.type = type;
        this.splittingType = splittingType;
        this.lander = lander;
        this.accounts = accounts;
        this.shares = shares;
    }

    public SplittingType getSplittingType() {
        return splittingType;
    }

    public Account getLander() {
        return lander;
    }

    public AccountWrapperList getWrappedAccounts() {
        return accounts;
    }

    public ExpenseWrapperMap getShares() {
        return shares;
    }

    public ExpenseType getType() {
        return type;
    }
}
