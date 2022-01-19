package com.example.splitwise.service;

import com.example.splitwise.model.User;
import com.example.splitwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Service
@Transactional
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
    public User getById(Integer userId) {
        return userRepository.getById(userId);
    }

    @Override
    public Collection<User> getAll(Set<Integer> ids) {
        return userRepository.getAll(ids);
    }

    @Override
    public void delete(Integer userId) {
        userRepository.delete(userId);
    }

    @Override
    public void changePassword(Integer id, String oldPassword, String newPassword) {
        userRepository.changePassword(id, oldPassword, newPassword);
    }

    @Override
    public void changeEmail(Integer userId, String email) {
        userRepository.changeEmail(userId, email);
    }

    @Override
    public void forgotPassword(Integer id) {
        userRepository.forgotPassword(id);
    }

    @Override
    public Integer getIdFromAuthenticationName(String name) {
        return userRepository.getIdFromAuthenticationName(name);
    }
}
