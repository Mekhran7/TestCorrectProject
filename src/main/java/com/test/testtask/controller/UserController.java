package com.test.testtask.controller;


import com.test.testtask.entity.User;
import com.test.testtask.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "user controller",description = "изменение,удаление,добавление,поиск пользователей")
public class UserController {

    @Autowired
    private UserService userService;
    @Operation(summary = "получение пользователей",description = "позволяет получать всех пользователей из БД")
    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "получение пользователя",description = "позволяет получать пользователя по ID из БД")
    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();}

    @Operation(summary ="создание и добавление пользователя"
            ,description = "позволяет создавать пользователя и добавлять в БД")
    @PostMapping("/createUser")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);}

    @Operation(summary = "изменение пользователя по ID",description = "позволяет изменять пользователя по ID в БД")
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();}

    @Operation(summary = "удаление пользователя",description = "позволяет удалять пользователя по ID из БД")
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable int id) {
       userService.deleteUser(id);
    }
}
