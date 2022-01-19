package com.example.splitwise.controller.view;

import com.example.splitwise.auth.RestRequestService;
import com.example.splitwise.model.User;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/account")
public class UserController {

    private final UserService userService;

    private final RestRequestService restResponsesService;

    @Autowired
    public UserController(UserService userService,
                          RestRequestService restResponsesService) {
        this.userService = userService;
        this.restResponsesService = restResponsesService;
    }

    @GetMapping
    public String getUserAccount(Model model) {
        model.addAttribute("id", 1);
        return "redirect:/account/1";
    }

    @GetMapping("/{id}")
    public String getUserAccountWithId(@PathVariable("id") int id, Model model) {
        User user = restResponsesService.findUser(id);

        model.addAttribute("userObject", user);
        return "/account";
    }

    @PutMapping
    public String update(@RequestParam Integer id, User user) {
        userService.update(id, user);
        return "redirect:/";
    }
}
