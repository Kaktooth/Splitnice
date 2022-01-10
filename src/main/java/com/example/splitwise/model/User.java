package com.example.splitwise.model;

public class User implements Identifiable {

    private final Integer id;
    private final String email;
    private final String phone;
    private final String password;
    private final boolean enabled;

    public User(Integer id, String email, String phone, String password, boolean enabled) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
