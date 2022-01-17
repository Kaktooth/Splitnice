package com.example.splitwise.controller.view;

import com.example.splitwise.controller.rest.RestRequestService;
import com.example.splitwise.model.Currency;
import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseDto;
import com.example.splitwise.model.expense.ExpenseType;
import com.example.splitwise.model.expense.NamesParser;
import com.example.splitwise.model.expense.SplittingType;
import com.example.splitwise.service.AccountService;
import com.example.splitwise.service.GroupService;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/add-expense")
public class ExpenseCreationController {
    private final AccountService accountService;
    private final UserService userService;
    private final GroupService groupService;
    private final RestRequestService restResponsesService;

    @Autowired
    public ExpenseCreationController(RestRequestService restResponsesService,
                                     AccountService accountService,
                                     UserService userService,
                                     GroupService groupService) {
        this.restResponsesService = restResponsesService;
        this.accountService = accountService;
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping
    public String addAttributes(@RequestParam(value = "expenseType") String expenseType,
                                Model model) {
        model.addAttribute(expenseType);

        return "add-expense";
    }

    @PostMapping
    public String registerNewUser(@RequestParam(value = "names") String names,
                                  @RequestParam(value = "expenseName") String expenseName,
                                  @RequestParam(value = "amount") BigDecimal amount,
                                  @RequestParam(value = "currency") String currency,
                                  @RequestParam(value = "splitType") String splitType,
                                  @RequestParam(value = "expenseType") String expenseType) {

        Integer id = userService.getIdFromAuthenticationName(
            SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());

        Account account = accountService.getById(id);

        NamesParser namesParser = new NamesParser(accountService, groupService);
        List<Account> accounts;

        if (Objects.equals(expenseType, "INDIVIDUAL")) {

            accounts = namesParser.parseToAccounts(names);

            ExpenseDto expense = restResponsesService.createExpense(
                new ExpenseDto(
                    1,
                    new BigDecimal("3.3"),
                    OffsetDateTime.now(),
                    Currency.valueOf(currency),
                    ExpenseType.valueOf(expenseType),
                    SplittingType.valueOf(splitType),
                    account,
                    accounts,
                    new HashMap<>(),
                    id
                )
            );
        } else {

            accounts = namesParser.parseToGroupAccounts(1);
            Expense expense = restResponsesService.createExpense(
                new ExpenseDto(
                    1,
                    new BigDecimal("3.3"),
                    OffsetDateTime.now(),
                    Currency.valueOf(currency),
                    ExpenseType.valueOf(expenseType),
                    SplittingType.valueOf(splitType),
                    account,
                    accounts,
                    new HashMap<>(),
                    1
                )
            );
        }

        return "add-expense";
    }
}
