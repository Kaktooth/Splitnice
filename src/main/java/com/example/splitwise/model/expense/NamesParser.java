package com.example.splitwise.model.expense;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.service.AccountService;
import com.example.splitwise.service.GroupService;

import java.util.ArrayList;
import java.util.List;

public class NamesParser {

    private final AccountService accountService;
    private final GroupService groupService;

    public NamesParser(AccountService accountService, GroupService groupService) {
        this.groupService = groupService;
        this.accountService = accountService;
    }

    public List<Account> parseToAccounts(String names) {
        List<String> splitedNames = List.of(names.split(" "));
        List<Account> accounts = new ArrayList<>();

        for (String name : splitedNames) {
            accounts.add(accountService.getByUserEmail(name));
        }

        return accounts;
    }

    public List<Account> parseToGroupAccounts(Integer groupId) {
        return new ArrayList<>(groupService.getAccounts(groupId));
    }
}
