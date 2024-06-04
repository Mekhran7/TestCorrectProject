package com.test.testtask.service;


import com.test.testtask.entity.User;
import com.test.testtask.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User("Dima", 30, "password1");
        User user2 = new User("Kolya", 25, "password2");
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userServiceImpl.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Dima", result.get(0).getName());
        assertEquals("Kolya", result.get(1).getName());
    }

    @Test
    void testGetUserById() {
        User user = new User("Dima", 30, "password1");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userServiceImpl.getUserById(1);

        assertNotNull(result);
        assertEquals("Dima", result.getName());
        assertEquals(30, result.getAge());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userServiceImpl.getUserById(1));
        assertEquals("user is not found", exception.getMessage());
    }

    @Test
    void testCreateUser() {
        User user = new User("Dima", 30, "password1");

        when(userRepository.save(user)).thenReturn(user);

        User result = userServiceImpl.createUser(user);

        assertNotNull(result);
        assertEquals("Dima", result.getName());
        assertEquals(30, result.getAge());
        assertEquals("password1", result.getPassword());
    }

    @Test
    void testUpdateUser() {

        User existingUser = new User("Dima", 30, "password1");
        existingUser.setId(1);
        User updatedDetails = new User("Dmitriy", 31, "newpassword");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        //это имитирует сохранение обновленного пользователя в бд
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User result = userServiceImpl.updateUser(1, updatedDetails);

        assertNotNull(result);
        assertEquals("Dmitriy", result.getName());
        assertEquals(31, result.getAge());
        assertEquals("newpassword", result.getPassword());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1);

        userServiceImpl.deleteUser(1);

        verify(userRepository, times(1)).deleteById(1);
    }
}