package com.example.splitwise.service;

import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.group.AccountGroupInfo;
import com.example.splitwise.model.group.Group;
import com.example.splitwise.model.group.GroupRole;
import com.example.splitwise.repository.group.GroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> getAccountGroups(Integer accountId) {
        return groupRepository.getAccountGroups(accountId);
    }

    @Override
    public Group getByTitle(String title) {
        return groupRepository.getByTitle(title);
    }

    @Override
    public List<AccountGroupInfo> getAccounts(Integer groupId) {
        return groupRepository.getAccounts(groupId);
    }

    @Override
    public void addAccount(Account account, Group group) {
        groupRepository.addAccount(account, group);
    }

    @Override
    public Group add(Group group) {
        return groupRepository.add(group);
    }

    @Override
    public Group getById(Integer groupId) {
        return groupRepository.getById(groupId);
    }

    @Override
    public List<Group> getAll(Set<Integer> ids) {
        return null;
    }

    @Override
    public void delete(Integer groupId) {
        groupRepository.delete(groupId);
    }


}
