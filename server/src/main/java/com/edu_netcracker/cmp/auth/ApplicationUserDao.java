package com.edu_netcracker.cmp.auth;

import java.util.Optional;

public interface ApplicationUserDao {

    Optional<com.example.demo.auth.ApplicationUser> selectApplicationUserByUsername(String username);

}
