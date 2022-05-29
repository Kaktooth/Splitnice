package com.example.splitwise.service;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.group.AccountGroupInfo;
import com.example.splitwise.model.group.Group;
import com.example.splitwise.model.group.GroupRole;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GroupService extends EntityService<Group> {

    List<Group> getAccountGroups(Integer accountId);

    Group getByTitle(String title);

    List<AccountGroupInfo> getAccounts(Integer groupId);

    void addAccount(Account account, Group group);
}
