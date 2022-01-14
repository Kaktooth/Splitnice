package com.example.splitwise.controller.view;

import com.example.splitwise.controller.rest.RestRequestService;
import com.example.splitwise.model.Currency;
import com.example.splitwise.model.WrappedList;
import com.example.splitwise.model.account.Account;
import com.example.splitwise.model.account.AccountWrapperList;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.ExpenseDto;
import com.example.splitwise.model.expense.ExpenseType;
import com.example.splitwise.model.expense.ExpenseWrapperMap;
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
//                                  @RequestParam(value = "creatorId") Integer creatorId,
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
            AccountWrapperList accountWrapperList = new AccountWrapperList(accounts);

            ExpenseDto expense = restResponsesService.createExpense(
                new ExpenseDto(
                    1,
                    new BigDecimal("3.3"),
                    OffsetDateTime.now(),
                    Currency.valueOf(currency),
                    ExpenseType.valueOf(expenseType),
                    SplittingType.valueOf(splitType),
                    account,
                    accountWrapperList,
                    new ExpenseWrapperMap(new HashMap<>()),
                    id
                )
            );
//                new IndividualExpense(
//                    null,
//                    new BigDecimal("3.3"),
//                    OffsetDateTime.now(),
//                    Currency.valueOf(currency),
//                    accounts.get(0).getId(),
//                    accounts.get(1).getId()      //only 0 to 1
//                )
//                new ExpenseDto(
//                    1,
//                    new BigDecimal("3.3"),
//                    OffsetDateTime.now(),
//                    Currency.valueOf(currency),
//                    ExpenseType.valueOf(expenseType),
//                    SplittingType.valueOf(splitType),
//                    account,
//                    accounts,
//                    null,
//                    1 //user mock
//                )
//            );

        } else {

            accounts = namesParser.parseToGroupAccounts(1);
            AccountWrapperList accountWrapperList = new AccountWrapperList(accounts);
            Expense expense = restResponsesService.createExpense(
                new ExpenseDto(
                    1,
                    new BigDecimal("3.3"),
                    OffsetDateTime.now(),
                    Currency.valueOf(currency),
                    ExpenseType.valueOf(expenseType),
                    SplittingType.valueOf(splitType),
                    account,
                    accountWrapperList,
                    null,
                    1 //user mock
                )
            );
        }


        return "add-expense";
    }
}
