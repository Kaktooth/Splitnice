package com.example.splitwise.controller.view;

import com.example.splitwise.model.Currency;
import com.example.splitwise.model.User;
import com.example.splitwise.model.account.Account;
import com.example.splitwise.service.AccountService;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Objects;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    private final AccountService accountService;

    @Autowired
    public AccountController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping
    public String getUser(@RequestParam(value = "edit", required = false) String edit, Model model) {
        Integer id = userService.getIdFromAuthenticationName(
            SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());

        User user = userService.getById(id);
        Account account = accountService.getById(id);
        BigDecimal moneyAmount = account.getMoneyAmount();
        Currency currency = account.getCurrency();
        model.addAttribute("amount", moneyAmount.toString() + currency.toString());
        model.addAttribute("edit", edit);
        model.addAttribute("savedEdit", edit);
        model.addAttribute("userObject", user);

        return "/account";
    }

    @PutMapping("/edit")
    public String update(@RequestParam(value = "edit") String edit,
                         @RequestParam(value = "email", required = false) String email,
                         @RequestParam(value = "old-password", required = false) String oldPassword,
                         @RequestParam(value = "new-password", required = false) String newPassword,
                         Model model) {

        Integer id = userService.getIdFromAuthenticationName(
            SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        User updatedUser = userService.getById(id);

        if (Objects.equals(edit, "email")) {
            userService.changeEmail(id, email);
        } else {
            userService.changePassword(id, oldPassword, newPassword);
        }

        model.addAttribute("userObject", updatedUser);
        model.addAttribute("savedEdit", edit);
        return "/account";
    }
}
