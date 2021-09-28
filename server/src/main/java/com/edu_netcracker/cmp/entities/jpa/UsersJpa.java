package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.AbstractJpaDao;
import com.edu_netcracker.cmp.entities.DTO.UserDTO;
import com.edu_netcracker.cmp.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersJpa extends AbstractJpaDao<User, String> {
    public UsersJpa() {
        super.setClazz(User.class);
    }

    public User findUsersByUserName(String name) {
        EntityManager entityManager = super.entityManager;
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.userName = ?1", User.class);
        User user = query.setParameter(1, name).getResultList().stream().findFirst().orElse(null);
        return user;
    }

    public List<UserDTO> findUsersByUserNameDTO() {
        List<User> all = super.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        all.forEach(x -> userDTOS.add(new UserDTO(x.getUserName(), x.getFIO())));
        return userDTOS;
    }
}
