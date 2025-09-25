package org.fomenko;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        userService.addUser(new User(1, "Maksim", "maksim@gmail.com"));
        userService.addUser(new User(2, "Ksenia", "ksenia@gmail.com"));
    }

    @Test
    void testGetUserByNameReturnsCurrentUser() {
        User user = new User(3, "Alina", "alina@gmail.com");
        userService.addUser(user);

        User result = userService.getUserByName("Alina");

        assertNotNull(result);
        assertEquals("Alina", result.getName());
        assertEquals("alina@gmail.com", result.getEmail());
    }

    @Test
    void testGetUserNameReturnNullIfNotFound() {
        userService.addUser(new User(10, "Alex", "alex@gmail.com"));

        User result = userService.getUserByName("Marina");

        assertNull(result);
    }

    @Test
    void testUserIsAddedToList() {
        User user = new User(10, "Valentin", "valentin@gmail.com");
        userService.addUser(user);

        List<User> testUsersList = userService.getAllUsers();

        assertTrue(testUsersList.contains(user));
    }

    @Test
    void testDuplicateIdThrowsException() {
        User testUser = new User(1, "Valentin", "valentin@gmail.com");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(testUser));

        assertEquals("User with ID " + testUser.getId() + " already exists", exception.getMessage());
    }

    @Test
    void testDuplicateEmailThrowsException() {
        User testUser = new User(3, "Valentin", "maksim@gmail.com");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(testUser));

        assertEquals("User with email " + testUser.getEmail() + " already exists", exception.getMessage());
    }

    @Test
    void successfullyAddingMultipleUsers() {
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void testCheckEmptyList() {
        userService = new UserService();
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
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.editUser(99, "Ghost", "ghost@gmail.com")
        );

        assertEquals("User with ID 99 does not exist", exception.getMessage());
    }

    @Test
    void testDeleteUserSuccessfully() {
        User testUser = userService.getUserById(1);

        userService.deleteUser(1);
        assertFalse(userService.getAllUsers().contains(testUser));
    }

    @Test
    void testDeleteUserThrowsExceptionIfNotFound() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.deleteUser(99));

        assertEquals("User with ID 99 does not exist", exception.getMessage());
    }
}
