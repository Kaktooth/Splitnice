package com.example.splitwise.service;

import com.example.splitwise.model.User;
import com.example.splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User add(User user) {
        return userRepository.add(user);
    }

    @Override
    public User update(Integer id, User user) {

        return user;
    }

    @Override
    public User getById(Integer userId) {
        return new User(userId, "mock@gmail.com", "0992397893", "qweqwe", true);
    }

    @Override
    public Collection<User> getAll(Set<Integer> ids) {
        return null;
    }

    @Override
    public void delete(Integer userId) {
    }

    @Override
    public User changePassword(Integer id,String oldPassword, String newPassword) {

        return new User(1, "mock@gmail.com", "0992397893", newPassword, true);
    }

    @Override
    public User changeEmail(Integer id) {
        return new User(1, "changed@gmail.com", "0992397893", "qweqwe", true);
    }

    @Override
    public User changePhoneNumber(Integer id) {
        return new User(1, "changed@gmail.com", "0334343422", "qweqwe", true);
    }

    @Override
    public User forgotPassword(Integer id) {
return null;
    }

    @Override
    public User changeSignInStatus(boolean signed) {
return null;
    }
}
