package org.fomenko.dto;

import lombok.Data;

@Data
public class PetDTO {
    private Integer id;
    private String name;
    private Integer age;
    private String type;
    private UserDTO user;
}
