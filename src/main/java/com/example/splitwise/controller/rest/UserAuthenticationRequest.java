package com.example.splitwise.controller.rest;

public class UserAuthenticationRequest {

    public UserAuthenticationRequest() {
    }

    public UserAuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

