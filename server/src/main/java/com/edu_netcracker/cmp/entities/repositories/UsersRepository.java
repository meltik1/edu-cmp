package com.edu_netcracker.cmp.entities.repositories;

import com.edu_netcracker.cmp.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface UsersRepository extends JpaRepository<User, String> {
    User findUsersByUserName(String name);

}
