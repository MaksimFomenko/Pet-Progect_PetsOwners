package org.fomenko.service.user_service;

import org.fomenko.exception.UserByIdNotFoundException;
import org.fomenko.exception.UserByNameNotFoundException;
import org.fomenko.exception.UserUpdateException;
import org.fomenko.model.User;
import org.fomenko.repository.user_repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User addUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() != null && e.getCause().getMessage().contains("unique")) {
                throw new IllegalStateException("User with email: " + user.getEmail() + " already exists");
            }
            throw e;
        }
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserByIdNotFoundException(id));
    }

    public List<User> getUsersByName(String name) {
        List<User> users = userRepository.findAllByName(name);
        if (users.isEmpty()) {
            throw new UserByNameNotFoundException(name);
        }
        return users;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserByID(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with ID: " + userId + " does not exist"));
        userRepository.delete(user);
    }

    public void deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User with email: " + email + " does not exist"));
        userRepository.delete(user);
    }

    public User updateUser(int userId, String newName, String newEmail) {
        User existingUser = getUserById(userId);

        existingUser.setName(newName);

        userRepository.findByEmail(newEmail)
                .filter(user -> !user.getId().equals(userId))
                .ifPresent(user -> {
                    throw new UserUpdateException("Email " + newEmail + " is already used by another user");
                });

        existingUser.setEmail(newEmail);

        return userRepository.save(existingUser);
    }
}
