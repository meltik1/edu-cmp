package com.edu_netcracker.cmp.RestControllers;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String email;
    private String password;
}
