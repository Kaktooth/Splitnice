package com.example.splitwise.controller.view;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseType;
import com.example.splitwise.model.expense.GroupExpense;
import com.example.splitwise.model.expense.IndividualExpense;
import com.example.splitwise.model.expense.SplittingType;
import com.example.splitwise.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
@RequestMapping("/add-expense")
public class ExpenseCreationController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseCreationController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public String addAttributes(@RequestParam(value = "expenseType") String expenseType, Model model) {
        model.addAttribute(expenseType);

        return "add-expense";
    }

    @PostMapping
    public String registerNewExpense(@RequestParam(value = "names") String name,
                                     @RequestParam(value = "expenseName") String title,
                                     @RequestParam(value = "amount") BigDecimal amount,
                                     @RequestParam(value = "currency") String currency,
                                     @RequestParam(value = "splitType") String splitType,
                                     @RequestParam(value = "expenseType") String expenseType) {

        Expense.ExpenseBuilder expenseBuilder = new Expense.ExpenseBuilder()
            .withAmount(amount)
            .withTitle(title)
            .withCurrency(Currency.valueOf(currency))
            .withSplittingType(SplittingType.valueOf(splitType));

        if (expenseType.equals(ExpenseType.INDIVIDUAL.toString())) {
            IndividualExpense individualExpense = expenseBuilder
                .buildIndividualExpense();
            expenseService.addNewIndividualExpense(individualExpense, name);
        } else if (expenseType.equals(ExpenseType.GROUP.toString())) {
            GroupExpense groupExpense = expenseBuilder
                .buildGroupExpense();
            expenseService.addNewGroupExpense(groupExpense, name);
        }

        return "redirect:/dashboard/expenses?expenses";
    }
}
