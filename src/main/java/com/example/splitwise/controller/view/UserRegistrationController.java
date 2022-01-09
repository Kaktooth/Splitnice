package com.example.splitwise.controller.view;

import com.example.splitwise.model.User;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/sign-up")
class UserRegistrationController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getRegisterPage() {
        return "/sign-up";
    }

    @PostMapping
    public String registerNewUser(@RequestParam(value = "user") String username,
                                  @RequestParam(value = "password") String password,
                                  @RequestParam(value = "phone") String phoneNumber) {

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(4, username, phoneNumber, encodedPassword, false);

        User addedUser = userService.add(user);

        return "/sign-in";
    }
}
