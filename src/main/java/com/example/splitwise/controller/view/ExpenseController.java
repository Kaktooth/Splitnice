package com.example.splitwise.controller.view;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.User;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.SplittingType;
import com.example.splitwise.model.transaction.Transaction;
import com.example.splitwise.service.AccountService;
import com.example.splitwise.service.ExpenseService;
import com.example.splitwise.service.TransactionMessageCreator;
import com.example.splitwise.service.TransactionService;
import com.example.splitwise.service.UserService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    private final TransactionService transactionService;

    private final UserService userService;

    private final AccountService accountService;

    public ExpenseController(ExpenseService expenseService, UserService userService,
                             TransactionService transactionService, AccountService accountService) {

        this.expenseService = expenseService;
        this.userService = userService;
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping
    public String getExpensesAccount(Model model) {

        model.addAttribute("split", SplittingType.values());
        model.addAttribute("currency", Currency.values());

        return "redirect:/dashboard/expenses/1?expenses&pageSize=4";
    }

    @PostMapping("/pay/{id}")
    public String payMoney(@PathVariable("id") Integer id,
                           @RequestParam("currentAmount") BigDecimal currentAmount,
                           @RequestParam("targetId") Integer targetId,
                           @RequestParam("transactionAmount") BigDecimal transactionAmount,
                           Model model) {

        BigDecimal totalAmount = currentAmount.min(transactionAmount);

        accountService.setMoneyAmount(
            targetId,
            totalAmount
        );

        return "redirect:/dashboard/expenses/1?expenses&pageSize=4";
    }

    @GetMapping("/{currentPage}")
    public String getExpenses(@PathVariable("currentPage") int currentPage,
                              @RequestParam("pageSize") Integer pageSize,
                              Model model) {

        List<Expense> expenses = expenseService.getUserExpenses(SecurityContextHolder.getContext()
            .getAuthentication()
            .getName());

        Pagination<Expense> pagination = new Pagination<>(expenses);
        int pageCount = pagination.getPageCount(pageSize) + 1;
        List<Integer> pageNumbers = pagination.getPageNumbers(pageCount);
        List<Expense> currentPageContent = pagination.getCurrentPageContent(currentPage - 1, pageSize);

        List<User> users = new ArrayList<>();
        if (expenses.size() > 0) {

            expenses.forEach(expense -> users.add(
                userService.getById(
                    expense.getCreatorId()
                )
            ));

            TransactionMessageCreator transactionMessageCreator = new TransactionMessageCreator(
                userService, transactionService
            );
            List<List<Transaction>> transactions = transactionMessageCreator.getTransactions(expenses);
            List<List<String>> messages = transactionMessageCreator.getTransactionMessages(expenses);

            model.addAttribute("transactions", transactions);
            model.addAttribute("transactionMessages", messages);
            model.addAttribute("users", users);
        }

        String userEmail = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

        Integer currentId = userService.getIdFromAuthenticationName(userEmail);

        BigDecimal amount = accountService.getByUserEmail(userEmail).getMoneyAmount();

        model.addAttribute("currentAmount", amount);
        model.addAttribute("currentId", currentId);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("expenseList", currentPageContent);

        return "dashboard";
    }
}

