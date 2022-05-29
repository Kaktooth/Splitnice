package com.example.splitwise.repository.group;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.group.AccountGroupInfo;
import com.example.splitwise.model.group.Group;
import com.example.splitwise.repository.EntityRepository;

import java.util.List;
import java.util.Map;

public interface GroupRepository extends EntityRepository<Group> {

    List<Group> getAccountGroups(Integer accountId);

    Group getByTitle(String title);

    List<AccountGroupInfo> getAccounts(Integer groupId);

    void addAccount(Account account, Group group);
}
