package com.example.splitwise.model;

public final class UserBuilder {

    private Integer id;
    private String email;
    private String phone;
    private String password;
    private boolean enabled;

    public UserBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public User build() {
        return new User(id, email, phone, password, enabled);
    }
}
