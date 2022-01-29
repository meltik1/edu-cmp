package com.edu_netcracker.cmp.restControllers;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String email;
    private String password;
}
