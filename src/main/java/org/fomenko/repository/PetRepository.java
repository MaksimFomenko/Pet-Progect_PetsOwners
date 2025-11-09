package org.fomenko.repository;

import org.fomenko.model.Pet;
import org.fomenko.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findAllByUserId(Integer ownerId);

    List<Pet> findAllByName(String name);

    List<Pet> findAllByAge(int age);

    List<Pet> findAllByType(String type);

    List<Pet> findAllByUser(User user);
}
