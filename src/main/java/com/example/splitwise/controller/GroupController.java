package com.example.splitwise.controller;

import com.example.splitwise.model.group.Group;
import com.example.splitwise.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> getAll() {
//        groupService.getAll();
        List<Group> groups = new ArrayList<>();
        Group group1 = new Group(1, "title1", 1);
        Group group2 = new Group(2, "title2", 2);
        groups.add(group1);
        groups.add(group2);

        return groups;
    }

    @GetMapping("/{id}")
    public Group getById(@PathVariable("id") Integer id) {
//        groupService.getById(id);
        Group group1 = new Group(1, "title1", 1);
        return group1;
    }

    @PostMapping
    public Group add(@RequestBody Group group) {
//        groupService.add(group);
        Group group1 = new Group(1, "title1", 1);
        return group1;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
//        groupService.delete(id);
    }
}
