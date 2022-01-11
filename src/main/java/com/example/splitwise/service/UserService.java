package com.example.splitwise.service;

import com.example.splitwise.model.User;

public interface UserService extends EntityService<User> {

    User changePassword(Integer id,String oldPassword, String newPassword);

    User update(Integer id, User user);

    User changeEmail(Integer id);

    User changePhoneNumber(Integer id);

    User forgotPassword(Integer id);

    User changeSignInStatus(boolean signed);
}
