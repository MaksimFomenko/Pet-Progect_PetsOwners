package org.fomenko.repository;

import org.fomenko.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(int id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAll();

    void deleteById(int id);
    void deleteByEmail(String email);
    void deleteAllUsers();
}
