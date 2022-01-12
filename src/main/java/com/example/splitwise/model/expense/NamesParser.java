package com.example.splitwise.model.expense;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.service.AccountService;
import com.example.splitwise.service.GroupService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NamesParser {
    AccountService accountService;
    GroupService groupService;

    public List<Account> parseToAccounts(String names) {
        List<String> splitedNames = List.of(names.split(" "));
        List<Account> accounts = new ArrayList<>();

        splitedNames
            .forEach(
                name -> accounts.add(
                    accountService.getAccountFromName(name)
                )
            );

        return accounts;
    }

    public List<Account> parseToGroupAccounts(Integer groupId) {

        List<Account> accounts = new ArrayList<>(groupService.getAccounts(groupId));

        return accounts;
    }
}
