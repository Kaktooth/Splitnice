package com.example.splitwise.repository.group;

import com.example.splitwise.model.group.Group;
import com.example.splitwise.repository.EntityRepository;

import java.util.List;

public interface GroupRepository extends EntityRepository<Group> {

    List<Group> getAccountGroups(Integer accountId);
}
