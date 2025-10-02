package org.fomenko.repository.pet_repository;

import org.fomenko.model.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {
    void save(Pet pet);

    Optional<Pet> findById(int id);
    List<Pet> findAllByName(String name);
    List<Pet> findAllByAge(int age);
    List<Pet> findAllByType(String type);
    List<Pet> findAllByOwner(int ownerId);

    void deleteById(int id);
}
