package org.fomenko.repository.user_repository;

import org.fomenko.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements UserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public Optional<User> findById(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }

    @Override
    public List<User> findAllByUsername(String username) {
        return users.stream()
                .filter(user -> user.getName().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void deleteById(int id) {
        users.removeIf(user -> user.getId() == id);
    }

    @Override
    public void deleteByEmail(String email) {
        users.removeIf(user -> user.getEmail().equals(email));
    }

    @Override
    public void deleteAllUsers() {
        users.clear();
    }
}
