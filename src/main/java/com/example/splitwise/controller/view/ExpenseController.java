package com.example.splitwise.controller.view;

import com.example.splitwise.controller.rest.RestRequestService;
import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseBuilder;
import com.example.splitwise.model.expense.SplittingType;
import com.example.splitwise.utils.Pageble;
import com.example.splitwise.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/dashboard/expenses")
public class ExpenseController {

    private final RestRequestService restResponsesService;

    @Autowired
    public ExpenseController(RestRequestService restResponsesService) {
        this.restResponsesService = restResponsesService;
    }

    @GetMapping
    public String getExpensesAccount(Model model) {


        model.addAttribute("split", SplittingType.values());
        model.addAttribute("currency", Currency.values());

        return "redirect:/dashboard/expenses/1?expenses&pageSize=4";
    }

    @GetMapping("/{currentPage}")
    public String getExpensesWithUserId(@PathVariable("currentPage") int currentPage,
                                        @RequestParam("pageSize") Integer pageSize,
                                        Model model) {

        List<Expense> expenseList = new ArrayList<>();

        Expense expense = new ExpenseBuilder()
            .withId(1)
            .withAmount(new BigDecimal("13.5"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.USD)
            .withCreatorId(1)
            .buildIndividualExpense();
        expenseList.add(expense);

        Expense expense2 = new ExpenseBuilder()
            .withId(2)
            .withAmount(new BigDecimal("100"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(1)
            .buildIndividualExpense();
        expenseList.add(expense2);

        Expense expense3 = new ExpenseBuilder()
            .withId(3)
            .withAmount(new BigDecimal("76.2"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.USD)
            .withCreatorId(2)
            .buildIndividualExpense();
        expenseList.add(expense3);

        Expense expense4 = new ExpenseBuilder()
            .withId(4)
            .withAmount(new BigDecimal("14.5"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(2)
            .buildIndividualExpense();
        expenseList.add(expense4);

        Expense expense5 = new ExpenseBuilder()
            .withId(5)
            .withAmount(new BigDecimal("176.2"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.USD)
            .withCreatorId(2)
            .buildIndividualExpense();
        expenseList.add(expense5);

        Expense expense6 = new ExpenseBuilder()
            .withId(6)
            .withAmount(new BigDecimal("144.5"))
            .withCreationDate(OffsetDateTime.now())
            .withCurrency(Currency.EUR)
            .withCreatorId(3)
            .buildIndividualExpense();
        expenseList.add(expense6);

        Pageble<Expense> pagination = new Pagination<>(expenseList);
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

