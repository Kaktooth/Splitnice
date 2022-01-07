package com.example.splitwise.service;

import com.example.splitwise.model.Account;
import com.example.splitwise.model.Identifiable;
import com.example.splitwise.model.User;
import com.example.splitwise.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User add(User user) {
        return userRepository.add(user);
    }

    @Override
    public User getById(Integer userId) {
        return null;
    }

    @Override
    public Collection<User> getAll(Set<Integer> ids) {
        return null;
    }

    @Override
    public void delete(Integer userId) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public void changeEmail(String phoneNumber) {

    }

    @Override
    public void forgotPassword(String phoneNumber) {

    }

    @Override
    public void changeSignInStatus(boolean signed) {

    }
}
