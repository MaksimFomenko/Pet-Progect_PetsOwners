package org.fomenko.controller.user_controller;

import org.fomenko.controller.UserController;
import org.fomenko.exception.UserByIdNotFoundException;
import org.fomenko.model.User;
import org.fomenko.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void getUsers_ReturnUsersTest() throws Exception {
        User user1 = new User();
        user1.setName("Maksim");
        user1.setEmail("maksim@gmail.com");

        User user2 = new User();
        user2.setName("Alice");
        user2.setEmail("alice@example.com");

        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Maksim"))
                .andExpect(jsonPath("$[0].email").value("maksim@gmail.com"))
                .andExpect(jsonPath("$[1].name").value("Alice"))
                .andExpect(jsonPath("$[1].email").value("alice@example.com"));
    }

    @Test
    void getUserById_ReturnUserTest() throws Exception {
        User user = new User();
        user.setName("Maksim");
        user.setEmail("maksim@gmail.com");

        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maksim"))
                .andExpect(jsonPath("$.email").value("maksim@gmail.com"));
    }

    @Test
    void getUserById_NotFoundTest() throws Exception {
        int id = 999;
        when(userService.getUserById(id)).thenThrow(new UserByIdNotFoundException(id));

        mockMvc.perform(get("/api/users/999")).andExpect(status().isNotFound());
    }
}