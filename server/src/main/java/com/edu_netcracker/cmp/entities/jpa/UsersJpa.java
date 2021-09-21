package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.DTO.UserDTO;
import com.edu_netcracker.cmp.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersJpa extends JpaRepository<User, Long> {
    User findUsersByUserName(String name);
    @Query(value = "SELECT new com.edu_netcracker.cmp.entities.DTO.UserDTO(u.userName, u.FIO)  from User u")
    List<UserDTO> findUsersByUserNameDTO();
}
