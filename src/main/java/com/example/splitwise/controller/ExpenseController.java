package com.example.splitwise.controller;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseDto;
import com.example.splitwise.service.ExpenseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    Expense newExpense(@RequestBody ExpenseDto expense) {
        return null;
    }
}
