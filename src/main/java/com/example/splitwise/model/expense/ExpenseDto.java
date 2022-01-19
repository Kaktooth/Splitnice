package com.example.splitwise.model.expense;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.account.Account;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

public class ExpenseDto extends Expense {

    private final SplittingType splittingType;
    private final Account lander;
    private final List<Account> accounts;
    private final Map<Account, BigDecimal> shares;

    public ExpenseDto(Integer id,
                      BigDecimal amount,
                      OffsetDateTime creationDate,
                      Currency currency,
                      ExpenseType type,
                      SplittingType splittingType,
                      Account lander,
                      List<Account> accounts,
                      Map<Account, BigDecimal> shares) {
        super(id, amount, creationDate, currency, type);
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public Map<Account, BigDecimal> getShares() {
        return shares;
    }
}
