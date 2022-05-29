package com.example.splitwise.controller.rest;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.group.Group;
import com.example.splitwise.model.group.GroupDto;
import com.example.splitwise.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/group")
public class RestGroupController {

    private final GroupService groupService;

    @Autowired
    public RestGroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<Group> getAll() {
        List<Group> groups = new ArrayList<>();
        List<Expense> expenseList = new ArrayList<>();
        Expense expense = new Expense.ExpenseBuilder()
            .withId(1)
            .withAmount(new BigDecimal("15.9"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(3)
            .buildGroupExpense();
        expenseList.add(expense);
        List<Expense> expenseList2 = new ArrayList<>();
        Expense expense2 = new Expense.ExpenseBuilder()
            .withId(2)
            .withAmount(new BigDecimal("15.9"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(3)
            .buildGroupExpense();
        expenseList2.add(expense2);
//        Group group1 = new GroupDto(1, "Title1", 1,
//            expenseList
//        );
//        Group group2 = new GroupDto(2, "Title2", 1,
//            expenseList2
//        );
//        groups.add(group1);
//        groups.add(group2);

        return groups;
    }

    @GetMapping("/{id}")
    public Group getById(@PathVariable("id") Integer id) {
        return groupService.getById(id);
    }

    @PostMapping
    public Group add(@RequestBody Group group) {
        return groupService.add(group);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        groupService.delete(id);
    }
}
