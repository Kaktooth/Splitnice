package com.example.splitwise.controller.rest;

import com.example.splitwise.model.User;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/account")
public class RestAccountController {

    private final UserService userService;

    @Autowired
    public RestAccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User getUserAccount() {
        Integer id = userService.getIdFromAuthenticationName(
            SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        User user = userService.getById(id);

        return user;
    }

    @PutMapping
    public User update(String edit, String email, String oldPassword, String newPassword, String phone) {

        Integer id = userService.getIdFromAuthenticationName(
            SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
        User user = userService.getById(id);

        if (Objects.equals(edit, "email")) {
            userService.changeEmail(id, email);
        } else if (Objects.equals(edit, "password")) {
            userService.changePassword(id, oldPassword, newPassword);
        } else {
            user.setPhone(phone);
        }

        return user;
    }
}
