package com.example.splitwise.controller.view;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseType;
import com.example.splitwise.model.expense.GroupExpense;
import com.example.splitwise.model.expense.IndividualExpense;
import com.example.splitwise.model.expense.SplittingType;
import com.example.splitwise.service.ExpenseService;
import com.example.splitwise.utils.Pagination;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/dashboard/expenses")
    public String getExpensesAccount(Model model) {

        model.addAttribute("split", SplittingType.values());
        model.addAttribute("currency", Currency.values());

        return "redirect:/dashboard/expenses/1?expenses&pageSize=4";
    }

    @GetMapping("/dashboard/expenses/{currentPage}")
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

    @GetMapping("/add-expense")
    public String addAttributes(@RequestParam(value = "expenseType") String expenseType, Model model) {
        model.addAttribute(expenseType);

        return "add-expense";
    }

    @PostMapping("/add-expense")
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

        return "dashboard";
    }
}

