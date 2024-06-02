package com.test.testtask.service;

import com.test.testtask.entity.User;
import com.test.testtask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Transactional
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("user is not found"));
    }
    @Transactional
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(int id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("user is not found"));
        if (user != null) {
            user.setName(userDetails.getName());
            user.setAge(userDetails.getAge());
            user.setPassword(userDetails.getPassword());
            return userRepository.save(user);
        }
        return user;
    }
    @Transactional
    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
