package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionJPA extends JpaRepository<Session, Long> {
}
