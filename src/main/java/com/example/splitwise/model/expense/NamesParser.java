package com.example.splitwise.model.expense;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.service.AccountService;
import com.example.splitwise.service.GroupService;

import java.util.ArrayList;
import java.util.List;

public class NamesParser {

    AccountService accountService;
    GroupService groupService;

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
