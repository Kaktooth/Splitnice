package com.example.splitwise.controller.rest;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/dashboard/expenses")
public class RestExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public RestExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/{id}")
    public List<Expense> getExpenses(@PathVariable("id") Integer id) {
        List<Expense> expenses = new ArrayList<>(expenseService.getAllAccountExpenses(Set.of(id)));
        return expenses;

    }

}
