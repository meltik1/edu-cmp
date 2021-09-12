package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.Attributes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributesJPA extends JpaRepository<Attributes, Long> {
    Attributes findAttributesByAttributeName(String name);
}
