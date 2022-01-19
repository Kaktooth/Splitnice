package com.example.splitwise.controller.rest;

import com.example.splitwise.model.group.Group;
import com.example.splitwise.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/add-group")
public class RestGroupCreationController {

    private final GroupService groupService;

    @Autowired
    public RestGroupCreationController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public Group addGroup(Group group) {
        return groupService.add(group);
    }
}
