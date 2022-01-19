package com.example.splitwise.repository;

import com.example.splitwise.model.User;

public interface UserRepository extends EntityRepository<User> {

    void changePassword(Integer userId, String oldPassword, String password);

    void changeEmail(Integer userId, String email);

    void forgotPassword(Integer id);

    Integer getIdFromAuthenticationName(String name);
}

