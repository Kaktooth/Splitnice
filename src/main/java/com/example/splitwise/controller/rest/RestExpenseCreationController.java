package com.example.splitwise.controller.rest;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseType;
import com.example.splitwise.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/api/add-expense")
public class RestExpenseCreationController {

    private final ExpenseService expenseService;

    @Autowired
    public RestExpenseCreationController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense getExpenses() {

        return expenseService.add(
            new Expense(1, new BigDecimal("2.2"), OffsetDateTime.now(), Currency.USD, ExpenseType.INDIVIDUAL)
        );
    }
}
