package com.edu_netcracker.cmp.restControllers;

import com.edu_netcracker.cmp.entities.users.Role;
import lombok.Data;

@Data
public class UserCreationDto {
    private String userName;

    private String fio;

    private Role role;

    private String password;
}
