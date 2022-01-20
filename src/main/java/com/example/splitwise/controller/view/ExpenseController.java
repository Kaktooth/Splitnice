package com.example.splitwise.controller.view;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.expense.SplittingType;
import com.example.splitwise.model.transaction.Transaction;
import com.example.splitwise.service.ExpenseService;
import com.example.splitwise.service.TransactionService;
import com.example.splitwise.service.UserService;
import com.example.splitwise.utils.Pagination;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/dashboard/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    private final TransactionService transactionService;

    private final UserService userService;

    public ExpenseController(ExpenseService expenseService, UserService userService,
                             TransactionService transactionService) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.transactionService = transactionService;
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

        List<Expense> expenses = expenseService.getUserExpenses(SecurityContextHolder.getContext()
            .getAuthentication()
            .getName());

        Pagination<Expense> pagination = new Pagination<>(expenses);
        int pageCount = pagination.getPageCount(pageSize) + 1;
        List<Integer> pageNumbers = pagination.getPageNumbers(pageCount);
        List<Expense> currentPageContent = pagination.getCurrentPageContent(currentPage - 1, pageSize);
//
//        Integer userId = userService.getIdFromAuthenticationName(SecurityContextHolder.getContext()
//            .getAuthentication()
//            .getName());

        Set<Integer> expenseIds = new HashSet<>();
        for (Expense expense : expenses) {
            expenseIds.add(expense.getId());
        }

        List<Transaction> transactions = transactionService.getTransactionsFromExpense(expenseIds);

        model.addAttribute("transactions", transactions);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("expenseList", currentPageContent);

        return "dashboard";
    }
}

