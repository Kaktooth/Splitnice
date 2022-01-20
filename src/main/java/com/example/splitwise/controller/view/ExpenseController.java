package com.example.splitwise.controller.view;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.SplittingType;
import com.example.splitwise.service.ExpenseService;
import com.example.splitwise.utils.Pagination;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public String getExpensesAccount(Model model) {

        model.addAttribute("split", SplittingType.values());
        model.addAttribute("currency", Currency.values());

        return "redirect:/dashboard/expenses/1?expenses&pageSize=4";
    }

    @GetMapping("/{currentPage}")
    public String getExpenses(@PathVariable("currentPage") int currentPage,
                              @RequestParam("pageSize") Integer pageSize,
                              Model model) {

        Integer userId = 2;

        List<Expense> expenses = new ArrayList<>();

        Expense expense = new Expense.ExpenseBuilder()
            .withId(1)
            .withAmount(new BigDecimal("13.5"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.USD)
            .withCreatorId(userId)
            .buildIndividualExpense();
        expenses.add(expense);

        Expense expense2 = new Expense.ExpenseBuilder()
            .withId(2)
            .withAmount(new BigDecimal("100"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(userId)
            .buildIndividualExpense();
        expenses.add(expense2);

        Expense expense3 = new Expense.ExpenseBuilder()
            .withId(3)
            .withAmount(new BigDecimal("76.2"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.USD)
            .withCreatorId(userId)
            .buildIndividualExpense();
        expenses.add(expense3);

        Expense expense4 = new Expense.ExpenseBuilder()
            .withId(4)
            .withAmount(new BigDecimal("14.5"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(userId)
            .buildIndividualExpense();
        expenses.add(expense4);

        Expense expense5 = new Expense.ExpenseBuilder()
            .withId(5)
            .withAmount(new BigDecimal("176.2"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.USD)
            .withCreatorId(userId)
            .buildIndividualExpense();
        expenses.add(expense5);

        Expense expense6 = new Expense.ExpenseBuilder()
            .withId(6)
            .withAmount(new BigDecimal("144.5"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(userId)
            .buildIndividualExpense();
        expenses.add(expense6);

        Pagination<Expense> pagination = new Pagination<>(expenses);
        int pageCount = pagination.getPageCount(pageSize) + 1;
        List<Integer> pageNumbers = pagination.getPageNumbers(pageCount);
        List<Expense> currentPageContent = pagination.getCurrentPageContent(currentPage - 1, pageSize);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("expenseList", currentPageContent);

        return "dashboard";
    }
}

