package com.example.splitwise.service;

import com.example.splitwise.model.User;

public interface UserService extends EntityService<User> {

    void changePassword(Integer userId, String oldPassword, String password);

    void changeEmail(Integer userId, String email);

    void forgotPassword(Integer id);

    Integer getIdFromAuthenticationName(String name);
}
