package com.example.splitwise.controller;

import com.example.splitwise.model.User;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@RestController
@RequestMapping("/sign-up")
public class UserRegistrationController {

    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value="sign-up")
    String registerPage() throws IOException {
        //response.sendRedirect("sign-up");
        System.out.println("sign up");
        return "sign-up";
    }


    @PostMapping
    public RedirectView registerNewUser(@RequestParam(value = "user") String username,
                                        @RequestParam(value = "password") String password,
                                        @RequestParam(value = "phone") String phoneNumber) {

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(4, username, phoneNumber, encodedPassword, false);

        User addedUser = userService.add(user);

        return new RedirectView("/sign-in");
    }
}
