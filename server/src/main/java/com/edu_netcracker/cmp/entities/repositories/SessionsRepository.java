package com.edu_netcracker.cmp.entities.repositories;

import com.edu_netcracker.cmp.entities.Session;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionsRepository extends MongoRepository<Session, String> {
}
