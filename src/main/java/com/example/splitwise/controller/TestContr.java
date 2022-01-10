package com.example.splitwise.controller;

import com.example.splitwise.model.Balance;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class TestContr {

    @GetMapping
    public Balance test() {

        return new Balance(null, 1, 1);
    }
}
