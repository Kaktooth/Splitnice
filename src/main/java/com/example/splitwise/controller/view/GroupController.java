package com.example.splitwise.controller.view;

import com.example.splitwise.model.expense.Expense;
import com.example.splitwise.model.group.Group;
import com.example.splitwise.model.group.GroupDto;
import com.example.splitwise.model.group.GroupRole;
import com.example.splitwise.model.transaction.Transaction;
import com.example.splitwise.service.AccountService;
import com.example.splitwise.service.ExpenseService;
import com.example.splitwise.service.GroupService;
import com.example.splitwise.service.TransactionMessageCreator;
import com.example.splitwise.service.TransactionService;
import com.example.splitwise.service.UserService;
import com.example.splitwise.utils.CurrentUserGetter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;
    private final AccountService accountService;
    private final ExpenseService expenseService;
    private final TransactionService transactionService;

    public GroupController(GroupService groupService,
                           UserService userService,
                           AccountService accountService,
                           ExpenseService expenseService,
                           TransactionService transactionService) {
        this.groupService = groupService;
        this.userService = userService;
        this.accountService = accountService;
        this.expenseService = expenseService;
        this.transactionService = transactionService;
    }

    @GetMapping("/dashboard/groups")
    public String getGroupAccounts(Model model) {

        String currentUserEmail = CurrentUserGetter.getActiveUserEmail();
        Integer activeUserId = userService.getIdFromAuthenticationName(currentUserEmail);
        List<Group> accountGroups = groupService.getAccountGroups(activeUserId);
        TransactionMessageCreator transactionMessageCreator = new TransactionMessageCreator(
            userService, transactionService
        );
        List<Expense> expenses = new ArrayList<>();

        List<GroupDto> groupDtos = new ArrayList<>();
        for (Group group : accountGroups) {
            expenses.addAll(expenseService.getGroupExpenses(group.getId()));

            groupDtos.add(
                new GroupDto(group, expenseService.getGroupExpenses(group.getId()),
                    groupService.getAccounts(group.getId()))
            );
        }
        System.out.println(expenses);
        List<List<Transaction>> transactions = transactionMessageCreator.getTransactions(expenses);
        String userEmail = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();
        model.addAttribute("currentAccount", accountService.getByUserEmail(userEmail));
        model.addAttribute("transactions", transactions);
        model.addAttribute("groupList", groupDtos);
        model.addAttribute("ownerRole", GroupRole.OWNER);
        model.addAttribute("memberRole", GroupRole.MEMBER);

        return "dashboard";
    }

    @PostMapping("/dashboard/groups/pay/{expenseId}")
    public String payExpense(@PathVariable("expenseId") int expenseId,
                             @RequestParam("creatorId") int creatorId,
                             @RequestParam("groupId") int groupId,
                             @RequestParam("currentAmount") BigDecimal currentAmount,
                             @RequestParam("transactionAmount") BigDecimal transactionAmount,
                             Model model) {
        expenseService.pay(expenseId, creatorId, groupId);
        return "redirect:/dashboard/groups";
    }

    @GetMapping("/add-group")
    public String getPage() {
        return "add-group";
    }

    @PostMapping("/add-group")
    public String addNewGroup(@RequestParam(value = "title") String title) {

        Integer id = userService.getIdFromAuthenticationName(
            SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());

        groupService.add(new Group(1, title, id));
        return "redirect:/dashboard/groups?groups";
    }

    @DeleteMapping("/delete-group/{id}")
    public String deleteGroup(@PathVariable Integer id) {
        groupService.delete(id);
        return "redirect:/dashboard/groups?groups";
    }

    @PostMapping("/group/{id}/add-account")
    public String addUser(@PathVariable Integer id,
                          @RequestParam("account") String account) {
        System.out.println(account);
        System.out.println(id);
        Integer accountId = accountService.getByUserEmail(account).getId();
        groupService.addAccount(accountService.getById(accountId), groupService.getById(id));

        return "redirect:/dashboard/groups?groups";
    }
}
