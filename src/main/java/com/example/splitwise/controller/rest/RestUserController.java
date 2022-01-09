package com.example.splitwise.controller.rest;

import com.example.splitwise.model.User;
import com.example.splitwise.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/account")
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserAccountWithId(@PathVariable("id") Integer id) {
        return new User(id,"","","",false);
    }

    @PutMapping
    public User update(@RequestParam Integer id, @RequestBody User user) {
        return userService.update(id, user);
    }

}
