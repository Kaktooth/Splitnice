package com.example.splitwise.controller.view;

import com.example.splitwise.controller.rest.RestRequestService;
import com.example.splitwise.model.Currency;
import com.example.splitwise.model.User;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.SplittingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/dashboard/expenses")
public class ExpenseController {

    private final RestRequestService restResponsesService;

    @Autowired
    public ExpenseController(RestRequestService restResponsesService) {
        this.restResponsesService = restResponsesService;
    }

    @GetMapping
    public String getExpensesAccount(Model model) {
        model.addAttribute("id", 1);
        model.addAttribute("expenses", new ArrayList<>());
        model.addAttribute("split", SplittingType.values());
        model.addAttribute("currency", Currency.values());

        return "redirect:/dashboard/expenses/1?expenses";
    }

    @GetMapping("/{id}")
    public String getExpensesWithId(@PathVariable("id") int id, Model model) {
        ArrayList<Expense> expenses = restResponsesService.findExpenses(id);

        model.addAttribute("expenses", expenses);
        return "dashboard";
    }
}

