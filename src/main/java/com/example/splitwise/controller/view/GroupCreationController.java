package com.example.splitwise.controller.view;

import com.example.splitwise.model.group.Group;
import com.example.splitwise.service.GroupService;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("add-group")
public class GroupCreationController {

    private final UserService userService;

    private final GroupService groupService;

    @Autowired
    public GroupCreationController(GroupService groupService,
                                   UserService userService) {

        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping
    public String getPage() {
        return "add-group";
    }

    @PostMapping
    public String addNewGroup(@RequestParam(value = "title") String title) {

        Integer id = userService.getIdFromAuthenticationName(
            SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());

        groupService.add(new Group(1, title, id));
        return "dashboard";
    }
}