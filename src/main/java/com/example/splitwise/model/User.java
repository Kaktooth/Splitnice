package com.example.splitwise.model;

public class User implements Identifiable {

    private Integer id;
    private String email;
    private String phone;
    private String password;
    private boolean enabled;

    public User(Integer id, String email, String phone, String password, boolean enabled) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.enabled = enabled;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static final class UserBuilder {

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
}
