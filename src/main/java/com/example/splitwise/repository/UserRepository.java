package com.example.splitwise.repository;

import com.example.splitwise.model.User;

public interface UserRepository extends EntityRepository<User> {

    void changePassword(String oldPassword, String newPassword);

    void changeEmail(String phoneNumber);

    void forgotPassword(String phoneNumber);

    void changeSignInStatus(boolean signed);
}

