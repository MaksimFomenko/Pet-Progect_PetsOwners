package org.fomenko.service.pet_service_tests;

import jakarta.transaction.Transactional;
import org.fomenko.model.Pet;
import org.fomenko.model.User;
import org.fomenko.repository.pet_repository.PetRepository;
import org.fomenko.repository.user_repository.UserRepository;
import org.fomenko.service.pet_service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PetServiceTest {
    @Autowired
    private PetService petService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setName("Alice");
        testUser.setEmail("alice@gmail.com");
        testUser =  userRepository.save(testUser);
    }

    @Test
    void addPet_shouldSavePet() {
        Pet pet = new Pet("Sam", 3, "Cat", testUser);

        petService.addPet(pet);
        List<Pet> pets = petService.getAllPets();
        assertEquals(1, pets.size());
        assertEquals("Sam", pets.get(0).getName());
    }

    @Test
    void testShouldReturnAllPets() {
        Pet pet = new Pet("Sam", 3, "Cat", testUser);
        Pet  pet2 = new Pet("Tom", 7, "Dog", testUser);

        petService.addPet(pet);
        petService.addPet(pet2);

        assertEquals(2, petService.getAllPets().size());
    }

    @Test
    void testGetAllPetsByName() {
        Pet pet = new Pet("Sam", 3, "Cat", testUser);
        Pet pet2 = new Pet("Sam", 7, "Cat", testUser);

        petService.addPet(pet);
        petService.addPet(pet2);

        assertEquals(2, petService.getAllPetsByName("Sam").size());
    }

    @Test
    void testGetAllPetsByType() {
        Pet pet  = new Pet("Sam", 3, "Cat", testUser);
        Pet pet2  = new Pet("Sin", 19, "Cat", testUser);

        petService.addPet(pet);
        petService.addPet(pet2);
        assertEquals(2, petService.getAllPetsByType("Cat").size());
    }

    @Test
    void getAllPetsByUser() {
        Pet pet  = new Pet("Sam", 3, "Cat", testUser);
        Pet pet2  = new Pet("Sin", 19, "Cat", testUser);

        petService.addPet(pet);
        petService.addPet(pet2);

        assertEquals(2, petService.getAllPetsByUser(testUser.getId()).size());
    }

    @Test
    void deletePetById() {
        Pet pet = new Pet("Sam", 3, "Cat", testUser);

        petService.addPet(pet);

        petService.deletePetById(pet.getId());
        assertEquals(0, petService.getAllPetsByUser(testUser.getId()).size());
    }

    @Test
    void updatePet() {
        Pet existingPet = new Pet("Sam", 3, "Cat", testUser);

        petService.addPet(existingPet);

        petService.updatePet(existingPet.getId(), "Martin", 10, "Rabbit");

        Pet updated = petRepository.findById(existingPet.getId()).orElseThrow();
        assertEquals("Martin", updated.getName());
        assertEquals(10, updated.getAge());
        assertEquals("Rabbit", updated.getType());
    }
}