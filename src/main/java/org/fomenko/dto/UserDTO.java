package org.fomenko.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
}
 