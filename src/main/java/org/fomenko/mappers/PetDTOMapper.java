package org.fomenko.mappers;

import org.fomenko.dto.PetDTO;
import org.fomenko.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetDTOMapper {

    private final UserDTOMapper userDTOMapper;

    public PetDTOMapper(UserDTOMapper userDTOMapper) {
        this.userDTOMapper = userDTOMapper;
    }

    public PetDTO convertToPetDTO(Pet pet) {
        if (pet == null) return null;

        PetDTO dto = new PetDTO();
        dto.setId(pet.getId()); // добавляем id
        dto.setName(pet.getName());
        dto.setAge(pet.getAge());
        dto.setType(pet.getType());
        dto.setUser(userDTOMapper.convertToUserDTO(pet.getUser()));
        return dto;
    }

    public Pet convertToPet(PetDTO petDTO) {
        if (petDTO == null) return null;

        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setAge(petDTO.getAge());
        pet.setType(petDTO.getType());
        pet.setUser(userDTOMapper.convertToUser(petDTO.getUser()));
        return pet;
    }
}