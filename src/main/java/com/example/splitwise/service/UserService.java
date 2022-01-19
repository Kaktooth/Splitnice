package com.example.splitwise.service;

import com.example.splitwise.model.User;

public interface UserService extends EntityService<User> {

    void changePassword(String oldPassword, String newPassword);

    User update(Integer id, User user);

    void changeEmail(String phoneNumber);

    void forgotPassword(String phoneNumber);

    void changeSignInStatus(boolean signed);
}
