package org.fomenko.service;

import org.fomenko.model.User;
import org.fomenko.repository.UserRepository;

import java.util.*;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //add only new user to List. If User is existing throw Exception
    public void addUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new IllegalStateException("User with ID" + user.getId() + " already exists");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("User with email: " + user.getEmail() + " already exists");
        }

        userRepository.save(user);
    }

    //get User by ID
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User with ID: " + id + " does not exist"));
    }

    // get first User by name from list
    public User getUserByName(String name) {
        return userRepository.findByUsername(name).orElseThrow(() -> new IllegalArgumentException("User with name: " + name + " does not exist"));
    }

    // get all Users from list
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // delete user by ID. If ID does not exist throw exception
    public void deleteUserByID(int userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new IllegalArgumentException("User with ID: " + userId + " does not exist");
        }
    }

    // delete User by email
    public void deleteUserByEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            userRepository.deleteByEmail(email);
        } else {
            throw new IllegalArgumentException("User with email: " + email + " does not exist");
        }
    }

    // edit username and email
    public void editUser(int userId, String newName, String newEmail) {
        User existingUser = getUserById(userId);

        existingUser.setName(newName);
        existingUser.setEmail(newEmail);
        userRepository.save(existingUser);
        System.out.println("User is updated");
    }


}
