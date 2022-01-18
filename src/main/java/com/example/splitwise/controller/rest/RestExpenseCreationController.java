package com.example.splitwise.controller.rest;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseDto;
import com.example.splitwise.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/add-expense")
public class RestExpenseCreationController {

    private final ExpenseService expenseService;

    @Autowired
    public RestExpenseCreationController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense getExpenses(@RequestBody ExpenseDto expenseDto) {
        return expenseService.add(expenseDto);
    }
}
