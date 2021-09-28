package com.edu_netcracker.cmp.entities;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Slf4j
@Transactional(propagation = Propagation.NESTED)
public abstract class AbstractJpaDao< T extends Serializable, T2 extends Serializable> {
    private Class< T > clazz;

    @Qualifier("adminEntityManager")
    @PersistenceContext(unitName = "admin")
    @Autowired
    protected EntityManager entityManager;

    public void setClazz( Class< T > clazzToSet ) {

        this.clazz = clazzToSet;
    }
    public T findOne(T2 id ){
        return entityManager.find(clazz, id);
    }
    public List< T > findAll(){
        try {
            return entityManager.createQuery("from " + clazz.getName()).getResultList();
        }
        catch (NoResultException noResultException) {
            return null;
        }
    }
    public void save( T entity ){
        try {
            entityManager.merge(entity);
        }
        catch (RuntimeException e){
            log.error(e.getMessage());
        }
    }
    public void update( T entity ){

        entityManager.persist( entity );
    }
    public void delete( T entity ){

        entityManager.remove( entity );
    }
    public void deleteById( T2 entityId ){
        T entity = findOne( entityId );
        delete( entity );
    }
}
