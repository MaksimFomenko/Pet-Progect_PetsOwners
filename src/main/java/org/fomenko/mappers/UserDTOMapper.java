package org.fomenko.mappers;

import org.fomenko.dto.UserDTO;
import org.fomenko.model.User;
import org.fomenko.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {

    private final UserRepository userRepository;

    public UserDTOMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO convertToUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public User convertToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        if (userDTO.getId() != null) {
            // Достаём существующего пользователя из базы по ID
            return userRepository.findById(userDTO.getId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userDTO.getId()));
        }

        // Если ID нет — создаём нового пользователя
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}

