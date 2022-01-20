package com.example.splitwise.service;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.group.Group;

import java.util.Collection;
import java.util.List;

public interface GroupService extends EntityService<Group> {

    List<Group> getAccountGroups(Integer accountId);

    List<Account> getAccounts(Integer groupId);
}
