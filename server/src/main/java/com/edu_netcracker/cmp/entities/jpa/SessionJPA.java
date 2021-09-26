package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionJPA extends MongoRepository<Session, String> {
}
