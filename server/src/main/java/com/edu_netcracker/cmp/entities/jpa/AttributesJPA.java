package com.edu_netcracker.cmp.entities.jpa;

import com.edu_netcracker.cmp.entities.AbstractJpaDao;
import com.edu_netcracker.cmp.entities.Attributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Component
public class AttributesJPA extends AbstractJpaDao<Attributes, Long> {
    public AttributesJPA() {
        super.setClazz(Attributes.class);
    }

    public Attributes findAttributesByAttributeName(String name) {
        EntityManager entityManager = super.entityManager;
        TypedQuery<Attributes> query = entityManager.createQuery("select a from Attributes a where a.attributeName = ?1", Attributes.class);
        Attributes singleResult = query.setParameter(1, name).getResultList().stream().findFirst().orElse(null);
        return singleResult;
    }
}
