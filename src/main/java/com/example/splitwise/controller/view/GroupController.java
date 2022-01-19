package com.example.splitwise.controller.view;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.group.Group;
import com.example.splitwise.model.group.GroupDto;
import com.example.splitwise.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public String getGroupsAndExpenses(Model model) {

        List<Group> groups = new ArrayList<>();

        List<Expense> expenseList = new ArrayList<>();
        Expense expense = new Expense.ExpenseBuilder()
            .withId(1)
            .withAmount(new BigDecimal("15.9"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(3)
            .withGroupId(1)
            .buildGroupExpense();
        expenseList.add(expense);

        List<Expense> expenseList2 = new ArrayList<>();
        Expense expense2 = new Expense.ExpenseBuilder()
            .withId(2)
            .withAmount(new BigDecimal("15.9"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(3)
            .withGroupId(1)
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
}
