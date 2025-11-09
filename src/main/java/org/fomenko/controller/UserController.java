package org.fomenko.controller;

import lombok.RequiredArgsConstructor;
import org.fomenko.dto.UserDTO;
import org.fomenko.model.User;
import org.fomenko.service.UserService;
import org.fomenko.mappers.UserDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDTOMapper userDTOMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getAllUsers().stream().map(userDTOMapper::convertToUserDTO).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userDTOMapper.convertToUserDTO(user));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User createdUser = userService.addUser(User.builder().name(userDTO.getName()).email(userDTO.getEmail()).build());
        return ResponseEntity.ok(userDTOMapper.convertToUserDTO(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(id, userDTO.getName(), userDTO.getEmail());
        return ResponseEntity.ok(userDTOMapper.convertToUserDTO(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUserByID(id);
        return ResponseEntity.noContent().build();
    }
}