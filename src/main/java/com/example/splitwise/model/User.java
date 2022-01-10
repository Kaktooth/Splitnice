package com.example.splitwise.model;

public class User implements Identifiable {

    private Integer id;
    private String email;
    private String phone;
    private String password;
    private String passwordLengthChar;
    private boolean enabled;

    public User(){

    }

    public User(Integer id, String email, String phone, String password, boolean enabled) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.passwordLengthChar = getPasswordLengthChar();
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

    public String getPasswordLengthChar() {
        return "*".repeat(password.length());
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
