package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface StudentsJpa extends JpaRepository<Students, Long> {
}
