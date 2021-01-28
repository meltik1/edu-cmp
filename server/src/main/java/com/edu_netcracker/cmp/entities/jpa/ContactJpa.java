package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ContactJpa extends JpaRepository<Contacts, Long> {
}
