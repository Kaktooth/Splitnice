package com.example.splitwise.model.expense;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.service.AccountService;
import com.example.splitwise.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class NamesParser {

    private final AccountService accountService;
    private final GroupService groupService;

    @Autowired
    public NamesParser(AccountService accountService, GroupService groupService) {
        this.groupService = groupService;
        this.accountService = accountService;
    }

    public List<Account> parseToAccounts(String names) {
        List<String> splitedNames = List.of(names.split(" "));
        List<Account> accounts = new ArrayList<>();

        splitedNames
            .forEach(
                name -> accounts.add(
                    accountService.getByUsername(name)
                )
            );

        return accounts;
    }

    public List<Account> parseToGroupAccounts(Integer groupId) {
        return new ArrayList<>(groupService.getAccounts(groupId));
    }
}
