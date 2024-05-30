package com.test.testtask.controller;

import com.test.testtask.entity.User;
import com.test.testtask.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(UserController.class)
class UserControllerTest {

    private MockMvc mockMvc;// симулирует http-запрос в контроллер

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();//создаем mockMvc для userController-a
    }

    @Test
    void getAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User("Dima Kolokolov", 30, "password1"),
                new User("Kolya Poskin", 25, "password2")
        );

        given(userService.getAllUsers()).willReturn(users);//когда вызывается метода gelAllUsers возвращает users

        mockMvc.perform(get("/api/users/allUsers"))
                .andExpect(status().isOk()) //проверка того что ответ 200
                .andExpect(jsonPath("$", hasSize(2)))//проверка того что json массив содержит 2 объекта
                .andExpect(jsonPath("$[0].name", is("Dima Kolokolov"))) //проверка 1-ого элемента
                .andExpect(jsonPath("$[1].name", is("Kolya Poskin")));
    }

    @Test
    void getUserById() throws Exception {
        User user = new User(1,"Dima Kolokolov", 30, "password1");

        given(userService.getUserById(anyInt())).willReturn(user);

        mockMvc.perform(get("/api/users/getUser/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Dima Kolokolov")));
    }

    @Test
    void createUser() throws Exception {
        User user = new User("Dima Kolokolov", 30, "password1");

        given(userService.createUser(any(User.class))).willReturn(user);

        mockMvc.perform(post("/api/users/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Dima Kolokolov\", \"age\":30, \"password\":\"password1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Dima Kolokolov")));
    }

    @Test
    void updateUser() throws Exception {
        User user = new User(1,"Dima Kolokolov", 30, "password1");

        given(userService.updateUser(anyInt(), any(User.class))).willReturn(user);

        mockMvc.perform(put("/api/users/updateUser/1")
                        .contentType(MediaType.APPLICATION_JSON)//устанавливает тип содержимого запроса как json
                        .content("{\"name\":\"Dima Kolokolov\", \"age\":35, \"password\":\"password1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age", is(user.getAge())));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/delete/1"))
                .andExpect(status().isOk());
    }
}
