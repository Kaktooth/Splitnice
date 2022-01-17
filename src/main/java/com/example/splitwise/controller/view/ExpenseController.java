package com.example.splitwise.controller.view;

import com.example.splitwise.controller.rest.RestRequestService;
import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseBuilder;
import com.example.splitwise.model.expense.SplittingType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/dashboard/expenses")
public class ExpenseController {

    private final RestRequestService restResponsesService;

    public ExpenseController(RestRequestService restResponsesService) {
        this.restResponsesService = restResponsesService;
    }

    @GetMapping
    public String getExpensesAccount(Model model) {
        model.addAttribute("id", 1);
        List<Expense> expenseList = new ArrayList<>();
        Expense expense = new ExpenseBuilder()
            .withId(1)
            .withAmount(new BigDecimal("2.2"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.USD)
            .withCreatorId(1)
            .buildIndividualExpense();
        expenseList.add(expense);
        Expense expense2 = new ExpenseBuilder()
            .withId(2)
            .withAmount(new BigDecimal("5.5"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(1)
            .buildIndividualExpense();
        expenseList.add(expense);

        Expense expense3 = new ExpenseBuilder()
            .withId(1)
            .withAmount(new BigDecimal("2.2"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.USD)
            .withCreatorId(2)
            .buildIndividualExpense();
        expenseList.add(expense);
        Expense expense4 = new ExpenseBuilder()
            .withId(2)
            .withAmount(new BigDecimal("5.5"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(2)
            .buildIndividualExpense();
        expenseList.add(expense);
        model.addAttribute("expenseList", expenseList);

        model.addAttribute("split", SplittingType.values());
        model.addAttribute("currency", Currency.values());

        return "redirect:/dashboard/expenses/1?expenses";
    }

    @GetMapping("/{id}")
    public String getExpensesWithUserId(@PathVariable("id") int id, Model model) {
        List<Expense> expenseList = new ArrayList<>();
        Expense expense = new ExpenseBuilder()
            .withId(1)
            .withAmount(new BigDecimal("2.2"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.USD)
            .withCreatorId(1)
            .buildIndividualExpense();
        expenseList.add(expense);
        Expense expense2 = new ExpenseBuilder()
            .withId(2)
            .withAmount(new BigDecimal("5.5"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(1)
            .buildIndividualExpense();
        expenseList.add(expense);

        Expense expense3 = new ExpenseBuilder()
            .withId(1)
            .withAmount(new BigDecimal("2.2"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.USD)
            .withCreatorId(2)
            .buildIndividualExpense();
        expenseList.add(expense);

        Expense expense4 = new ExpenseBuilder()
            .withId(2)
            .withAmount(new BigDecimal("5.5"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(2)
            .buildIndividualExpense();
        expenseList.add(expense);

        model.addAttribute("expenseList", expenseList);
        return "dashboard";
    }
}

