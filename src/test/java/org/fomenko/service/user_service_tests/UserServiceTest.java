package org.fomenko.service.user_service_tests;

import jakarta.transaction.Transactional;
import org.fomenko.exception.UserByIdNotFoundException;
import org.fomenko.exception.UserByNameNotFoundException;
import org.fomenko.model.User;
import org.fomenko.repository.UserRepository;
import org.fomenko.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Transactional
    void testFindUserByNameReturnsCurrentUser() {
        User user = new User("Alina", "alina@gmail.com");
        userRepository.save(user);

        List<User> result = userService.getUsersByName("Alina");

        assertFalse(result.isEmpty(), "Список не должен быть пустым");
        assertEquals(1, result.size(), "Должен быть один пользователь с именем Alina");

        User foundUser = result.get(0);
        assertEquals("Alina", foundUser.getName());
        assertEquals("alina@gmail.com", foundUser.getEmail());
    }

    @Test
    @Transactional
    void testFindUserNameThrowExceptionIfNotFound() {
        userRepository.save(new User("Alex", "alex@gmail.com"));

        UserByNameNotFoundException exception = assertThrows(UserByNameNotFoundException.class, () -> userService.getUsersByName("NonExistentUser"));

        assertEquals("User with name NonExistentUser not found", exception.getMessage());
    }

    @Test
    @Transactional
    void testUserIsAddedToList() {
        User user = new User("Valentin", "valentin@gmail.com");
        userRepository.save(user);

        List<User> testUsersList = userRepository.findAll();

        assertTrue(testUsersList.contains(user));
    }

    @Test
    @Transactional
    void testAddUserDuplicateEmailThrowsException() {
        userRepository.save(new User("Maksim", "maksim@gmail.com"));

        User duplicateUser = new User("Valentin", "maksim@gmail.com");

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> userService.addUser(duplicateUser));

        assertEquals("User with email: maksim@gmail.com already exists", exception.getMessage());
    }

    @Test
    @Transactional
    void successfullyAddingMultipleUsers() {
        userRepository.save(new User("Maksim", "maksim@gmail.com"));
        userRepository.save(new User("Ksenia", "ksenia@gmail.com"));
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    @Transactional
    void testCheckEmptyList() {
        UserService userService = new UserService(userRepository);
        assertTrue(userService.getAllUsers().isEmpty(), "Список пользователей должен быть пустым после инициализации");
    }

    @Test
    @Transactional
    void testUpdateUserSuccessfully() {
        User savedUser = userRepository.save(new User("Maksim", "maksim@gmail.com"));

        User updatedUser = userService.updateUser(
                savedUser.getId(),
                "Max",
                "max@gmail.com"
        );

        assertEquals("Max", updatedUser.getName());
        assertEquals("max@gmail.com", updatedUser.getEmail());

        User userFromDb = userRepository.findById(savedUser.getId())
                .orElseThrow(() -> new UserByIdNotFoundException(savedUser.getId()));

        assertEquals("Max", userFromDb.getName());
        assertEquals("max@gmail.com", userFromDb.getEmail());
    }

    @Test
    @Transactional
    void testUpdateUserThrowsExceptionIfNotFound() {
        Exception exception = assertThrows(UserByIdNotFoundException.class, () -> userService.updateUser(99, "Ghost", "ghost@gmail.com"));

        assertEquals("User with id 99 not found", exception.getMessage());
    }

    @Test
    @Transactional
    void testDeleteUserByIDSuccessfully() {
        User testUser = userRepository.save(new User("Maksim", "maksim@gmail.com"));

        Integer userId = testUser.getId();

        userService.deleteUserByID(userId);

        assertFalse(userService.getAllUsers().stream().anyMatch(user -> user.getId().equals(userId)));
    }

    @Test
    @Transactional
    void testDeleteUserByIDThrowsExceptionIfNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteUserByID(99));

        assertEquals("User with ID: 99 does not exist", exception.getMessage());
    }
}
