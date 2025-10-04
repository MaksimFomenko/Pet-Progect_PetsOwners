package org.fomenko.repository.user_repository;

import org.fomenko.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByName(String name);

    Optional<User> findByEmail(String email);
}
