package com.example.splitwise.repository.user;

import com.example.splitwise.model.User;
import com.example.splitwise.repository.EntityRepository;

public interface UserRepository extends EntityRepository<User> {

    void changePassword(Integer userId, String oldPassword, String password);

    void changeEmail(Integer userId, String email);

    void forgotPassword(Integer id);

    Integer getIdFromAuthenticationName(String name);
}

