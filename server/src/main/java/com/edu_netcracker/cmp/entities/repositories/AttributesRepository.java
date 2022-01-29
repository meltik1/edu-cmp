package com.edu_netcracker.cmp.entities.repositories;

import com.edu_netcracker.cmp.entities.Attributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface AttributesRepository extends JpaRepository<Attributes, Long> {


    Attributes findAttributesByAttributeName(String name);;

}
