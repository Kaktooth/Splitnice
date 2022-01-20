package com.example.splitwise.controller.view;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.group.Group;
import com.example.splitwise.model.group.GroupDto;
import com.example.splitwise.service.GroupService;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupController {

    private final UserService userService;
    private final GroupService groupService;

    public GroupController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping("/dashboard/groups")
    public String getGroupsAndExpenses(Model model) {

        List<Group> groups = new ArrayList<>();

        List<Expense> expenseList = new ArrayList<>();
        Expense expense = new Expense.ExpenseBuilder()
            .withId(1)
            .withAmount(new BigDecimal("15.9"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(3)
//            .withGroupId(1)
            .buildGroupExpense();
        expenseList.add(expense);

        List<Expense> expenseList2 = new ArrayList<>();
        Expense expense2 = new Expense.ExpenseBuilder()
            .withId(2)
            .withAmount(new BigDecimal("15.9"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(3)
//            .withGroupId(1)
            .buildGroupExpense();
        expenseList2.add(expense2);

        Group group1 = new GroupDto(1, "Title1", 1,
            expenseList
        );

        Group group2 = new GroupDto(2, "Title2", 1,
            expenseList2
        );

        groups.add(group1);
        groups.add(group2);

        model.addAttribute("groupList", groups);

        return "dashboard";
    }

    @GetMapping("/add-group")
    public String getPage() {
        return "add-group";
    }

    @PostMapping("/add-group")
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
