package com.example.splitwise.controller.view;

import com.example.splitwise.model.group.Group;
import com.example.splitwise.model.group.GroupDto;
import com.example.splitwise.service.ExpenseService;
import com.example.splitwise.service.GroupService;
import com.example.splitwise.service.UserService;
import com.example.splitwise.utils.CurrentUserGetter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;
    private final ExpenseService expenseService;

    public GroupController(GroupService groupService,
                           UserService userService,
                           ExpenseService expenseService) {
        this.groupService = groupService;
        this.userService = userService;
        this.expenseService = expenseService;
    }

    @GetMapping("/dashboard/groups")
    public String getGroupAccounts(Model model) {

        String currentUserEmail = CurrentUserGetter.getActiveUserEmail();

        Integer activeUserId = userService.getIdFromAuthenticationName(currentUserEmail);

        List<Group> accountGroups = groupService.getAccountGroups(activeUserId);

        List<GroupDto> groupDtos = new ArrayList<>();
        for (Group group : accountGroups) {
            groupDtos.add(
                new GroupDto(group, expenseService.getUserExpenses(
                    userService.getById(
                        group.getCreatorId()
                    ).getEmail()
                ))
            );
        }
        model.addAttribute("groupList", groupDtos);

        return "dashboard";
    }

    @GetMapping("add-group")
    public String getPage() {
        return "add-group";
    }

    @PostMapping("add-group")
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
