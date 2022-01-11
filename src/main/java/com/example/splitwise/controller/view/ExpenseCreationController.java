package com.example.splitwise.controller.view;

import com.example.splitwise.controller.rest.RestRequestService;
import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseType;
import com.example.splitwise.model.expense.SplittingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Controller
@RequestMapping("/dashboard/new-expense")
public class ExpenseCreationController {

    private final RestRequestService restResponsesService;

    @Autowired
    public ExpenseCreationController(RestRequestService restResponsesService) {
        this.restResponsesService = restResponsesService;
    }

    @GetMapping
    public void addAttributes(Model model){

    }

    @PostMapping
    public String registerNewUser(@RequestParam(value = "user") String username,
                                  @RequestParam(value = "password") String password,
                                  @RequestParam(value = "phone") String phoneNumber) {

        Expense expense = restResponsesService.createExpense(new Expense(1, new BigDecimal("3.3"), OffsetDateTime.now(), Currency.USD, ExpenseType.INDIVIDUAL));

        return "new-expense";
    }
}
