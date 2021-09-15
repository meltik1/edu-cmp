package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersJpa extends JpaRepository<User, Long> {
    User findUsersByUserName(String name);
}
