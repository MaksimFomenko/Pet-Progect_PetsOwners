package org.fomenko.service.pet_service;

import jakarta.transaction.Transactional;
import org.fomenko.model.Pet;
import org.fomenko.repository.pet_repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet addPet(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
        return petRepository.save(pet);
    }

    public Pet getPetById(int id) {
        return petRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pet with ID " + id + " does not exist"));
    }


    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getAllPetsByName(String name) {
        return petRepository.findAllByName(name);
    }

    public List<Pet> getAllPetsByType(String type) {
        return petRepository.findAllByType(type);
    }

    public List<Pet> getAllPetsByUser(int userId) {
        return petRepository.findAllByUserId(userId);
    }

    public void deletePetById(int petId) {
        Pet pet = getPetById(petId);
        petRepository.delete(pet);
    }

    @Transactional
    public Pet updatePet(int petId, String newName, int newAge, String newType) {
        Pet existingPet = getPetById(petId);

        existingPet.setName(newName);
        existingPet.setAge(newAge);
        existingPet.setType(newType);

        return petRepository.save(existingPet);
    }
}
