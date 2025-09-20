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
    }

    @Test
    void testGetUserByNameReturnsCurrentUser() {
//        UserService userService = new UserService();
        User user = new User(3, "Alina", "alina@gmail.com");
        userService.addUser(user);

        User result = userService.getUserByName("Alina");

        assertNotNull(result);
        assertEquals("Alina", result.getName());
        assertEquals("alina@gmail.com", result.getEmail());
    }

    @Test
    void testGetUserNameReturnNullIfNotFound() {
//        UserService userService = new UserService();
        userService.addUser(new User(10, "Alex", "alex@gmail.com"));

        User result = userService.getUserByName("Maksim");

        assertNull(result);
    }

    @Test
    void testUserIsAddedToList() {
//        UserService userService = new UserService();
        User user = new User(10, "Valentin", "valentin@gmail.com");
        userService.addUser(user);

        List<User> testUsersList = userService.getAllUsers();

        assertTrue(testUsersList.contains(user));
    }

    @Test
    void testDuplicateIdThrowsException() {
        userService.addUser(new User(1, "Maksim", "maksim@gmail.com"));

        User testUser = new User(1, "Valentin", "valentin@gmail.com");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(testUser));

        assertEquals("User with id " + testUser.getId() + " already exists", exception.getMessage());
    }

    @Test
    void testDuplicateEmailThrowsException() {
        userService.addUser(new User(1, "Maksim", "maksim@gmail.com"));

        User testUser = new User(2, "Valentin", "maksim@gmail.com");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(testUser));

        assertEquals("User with email " + testUser.getEmail() + " already exists", exception.getMessage());
    }

    @Test
    void successfullyAddingMultipleUsers() {
        userService.addUser(new User(1, "Maksim", "maksim@gmail.com"));
        userService.addUser(new User(2, "Valentin", "valentin@gmail.com"));

        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void testCheckEmptyList() {
        assertTrue(userService.getAllUsers().isEmpty(), "Список пользователей должен быть пустым после инициализации");
    }
}
