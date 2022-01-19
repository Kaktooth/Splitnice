package com.example.splitwise.controller.rest;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.service.ExpenseService;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/dashboard/expenses")
public class RestExpenseController {

    private final ExpenseService expenseService;

    private final UserService userService;

    @Autowired
    public RestExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @GetMapping
    public List<Expense> getExpenses() {

        Integer userId = userService.getIdFromAuthenticationName(
            SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());

        return new ArrayList<>(expenseService.getAllAccountExpenses(Set.of(userId)));
    }

}
