package org.fomenko.repository.pet_repository;

import org.fomenko.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findAllByOwnerId(Integer ownerId);
//    void save(Pet pet);
//
//    Optional<Pet> findById(int id);
//    List<Pet> findAllByName(String name);
//    List<Pet> findAllByAge(int age);
//    List<Pet> findAllByType(String type);
//    List<Pet> findAllByOwner(int ownerId);
//
//    void deleteById(int id);
}
