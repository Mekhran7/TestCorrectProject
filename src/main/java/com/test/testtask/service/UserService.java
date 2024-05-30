package com.test.testtask.service;

import com.test.testtask.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();
    public User getUserById(int id);
    public User createUser(User user);
    public User updateUser(int id, User userDetails);
    public void deleteUser(int id);
}
