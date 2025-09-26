package org.fomenko.service;

import org.fomenko.model.User;

import java.util.*;

public class UserService {
    private final List<User> users = new ArrayList<>();
    private final Set<Integer> userIds = new HashSet<>();
    private final Set<String> userEmails = new HashSet<>();

    //add only new user to List. If User is existing throw Exception
    public void addUser(User user) {
        if (userIds.contains(user.getId())) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " already exists");
        } else if (userEmails.contains(user.getEmail())) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }

        users.add(user);
        userIds.add(user.getId());
        userEmails.add(user.getEmail());
    }

    // find User by ID. Return User or null
    public User findUserByID(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // delete User by email
    public void deleteUserByEmail(String email) {
        users.removeIf(user -> user.getEmail().equals(email));
    }

    // get all Users from list
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // get first User by name from list
    public User getUserByName(String name) {
        return users.stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
    }

    //get User by ID
    public User getUserById(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElseThrow(() ->
                new IllegalArgumentException("User with ID " + id + " does not exist"));
    }

    // edit user name and email
    public void editUser(int userId, String newName, String newEmail) {
        User existingUser = findUserByID(userId);
        if (existingUser == null) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist");
        }

        existingUser.setName(newName);
        existingUser.setEmail(newEmail);
        System.out.println("User is updated");
    }

    // delete user by ID. If ID does not exist throw exception
    public void deleteUser(int userId) {
        boolean removed = users.removeIf(u -> u.getId() == userId);
        if (!removed) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist");
        }
    }
}
