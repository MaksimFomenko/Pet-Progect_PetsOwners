package org.fomenko.service;

import org.fomenko.model.User;
import org.fomenko.repository.user_repository.InMemoryUserRepository;
import org.fomenko.repository.user_repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        userService = new UserService(userRepository);
        userRepository.save(new User(1, "Maksim", "maksim@gmail.com"));
        userRepository.save(new User(2, "Ksenia", "ksenia@gmail.com"));
    }

    @Test
    void testFindUserByNameReturnsCurrentUser() {
        User user = new User(3, "Alina", "alina@gmail.com");
        userRepository.save(user);

        List<User> result = userService.getUsersByName("Alina");

        assertFalse(result.isEmpty(), "Список не должен быть пустым");
        assertEquals(1, result.size(), "Должен быть один пользователь с именем Alina");

        User foundUser = result.get(0);
        assertEquals("Alina", foundUser.getName());
        assertEquals("alina@gmail.com", foundUser.getEmail());
    }

    @Test
    void testFindUserNameThrowExceptionIfNotFound() {
        userRepository.save(new User(10, "Alex", "alex@gmail.com"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUsersByName("NonExistentUser");
        });

        assertEquals("User with name: NonExistentUser does not exist", exception.getMessage());
    }

    @Test
    void testUserIsAddedToList() {
        User user = new User(10, "Valentin", "valentin@gmail.com");
        userRepository.save(user);

        List<User> testUsersList = userRepository.findAll();

        assertTrue(testUsersList.contains(user));
    }

    @Test
    void testDuplicateIdThrowsException() {
        User testUser = new User(1, "Valentin", "valentin@gmail.com");

        Exception exception = assertThrows(IllegalStateException.class, () -> userService.addUser(testUser));

        assertEquals("User with ID: " + testUser.getId() + " already exists", exception.getMessage());
    }

    @Test
    void testDuplicateEmailThrowsException() {
        User testUser = new User(3, "Valentin", "maksim@gmail.com");

        Exception exception = assertThrows(IllegalStateException.class, () -> userService.addUser(testUser));

        assertEquals("User with email: " + testUser.getEmail() + " already exists", exception.getMessage());
    }

    @Test
    void successfullyAddingMultipleUsers() {
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void testCheckEmptyList() {
        UserRepository repository = new InMemoryUserRepository();
        UserService userService = new UserService(repository);
        assertTrue(userService.getAllUsers().isEmpty(), "Список пользователей должен быть пустым после инициализации");
    }

    @Test
    void testEditUserSuccessfully() {
        userService.editUser(1, "Max", "max@gmail.com");

        User updatedUser = userService.getUserById(1);
        assertEquals("Max", updatedUser.getName());
        assertEquals("max@gmail.com", updatedUser.getEmail());
    }

    @Test
    void testEditUserThrowsExceptionIfNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.editUser(99, "Ghost", "ghost@gmail.com"));

        assertEquals("User with ID: 99 does not exist", exception.getMessage());
    }

    @Test
    void testDeleteUserByIDSuccessfully() {
        User testUser = userService.getUserById(1);

        userService.deleteUserByID(1);
        assertFalse(userService.getAllUsers().contains(testUser));
    }

    @Test
    void testDeleteUserByIDThrowsExceptionIfNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteUserByID(99));

        assertEquals("User with ID: 99 does not exist", exception.getMessage());
    }
}
