package com.example.splitwise.service;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.group.Group;

import java.util.Collection;

public interface GroupService extends EntityService<Group> {

    Collection<Account> getAccounts(Integer groupId);
}
