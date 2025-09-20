package org.fomenko;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserService {
    private final List<User> users = new ArrayList<>();
    private final Set<Integer> userIds = new HashSet<>();
    private final Set<String> userEmails = new HashSet<>();

    //add only new user to List. If User is existing throw Exception
    public void addUser(User user) {
        if (userIds.contains(user.getId())) {
            throw new IllegalArgumentException("User with id " + user.getId() + " already exists");
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

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public User getUserByName(String name) {
        return users.stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
    }
}
