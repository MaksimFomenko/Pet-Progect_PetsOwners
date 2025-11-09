package org.fomenko.controller;

import lombok.RequiredArgsConstructor;
import org.fomenko.dto.PetDTO;
import org.fomenko.mappers.PetDTOMapper;
import org.fomenko.mappers.UserDTOMapper;
import org.fomenko.model.Pet;
import org.fomenko.model.User;
import org.fomenko.service.PetService;
import org.fomenko.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final UserService userService;
    private final PetDTOMapper petDTOMapper;
    private final UserDTOMapper userDTOMapper;

    @GetMapping
    public ResponseEntity<List<PetDTO>> getAllPets() {
        List<PetDTO> pets = petService.getAllPets().stream().map(petDTOMapper::convertToPetDTO).toList();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getPetById(@PathVariable("id") Integer id) {
        Pet pet = petService.getPetById(id);
        return ResponseEntity.ok(petDTOMapper.convertToPetDTO(pet));
    }

    @PostMapping
    public ResponseEntity<PetDTO> createPet(@RequestBody PetDTO petDTO) {
        User user = userService.getUserById(petDTO.getUser().getId());

        Pet pet = Pet.builder()
                .name(petDTO.getName())
                .age(petDTO.getAge())
                .type(petDTO.getType())
                .user(user)
                .build();

        Pet createdPet = petService.addPet(pet);
        return ResponseEntity.ok(petDTOMapper.convertToPetDTO(createdPet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> updatePet(@PathVariable("id") Integer id, @RequestBody PetDTO petDTO) {
        Pet updatedPet = petService.updatePet(id, petDTO.getName(), petDTO.getAge(), petDTO.getType());
        return ResponseEntity.ok(petDTOMapper.convertToPetDTO(updatedPet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable("id") Integer id) {
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }
}
