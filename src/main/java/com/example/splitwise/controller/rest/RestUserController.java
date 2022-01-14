package com.example.splitwise.controller.rest;

import com.example.splitwise.model.User;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/account")
public class RestUserController {

    private final UserService userService;

    @Autowired
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User getUserAccount(Authentication authentication) {
        return new User(0, authentication.getName(), "", "", false);
    }

    @GetMapping("/{id}")
    public User getUserAccountWithId(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public User edit(@PathVariable("id") Integer id,
                       @RequestParam(value = "edit", required = false) String edit,
                       @RequestParam(value = "email", required = false) String email,
                       @RequestParam(value = "new-password", required = false) String password,
                       @RequestParam(value = "phone", required = false) String phone,
                       @RequestBody User user,
                       Model model) {
        if (Objects.equals(edit, "email")) {
            user.setEmail(email);
        } else if (Objects.equals(edit, "password")) {
            user.setPassword(password);
        } else {
            user.setPhone(phone);
        }

        model.addAttribute("userObject", user);
        return user;
    }
}
