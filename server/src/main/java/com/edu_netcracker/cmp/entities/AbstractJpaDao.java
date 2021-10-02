package com.edu_netcracker.cmp.entities;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Qualifier("hrEntityManager")
    @PersistenceContext(unitName = "admin")
    @Autowired
    protected EntityManager entityManagerHr;

    @Qualifier("userEntityManager")
    @PersistenceContext(unitName = "admin")
    @Autowired
    protected EntityManager entityManagerUser;

    private EntityManager getUserEntityManager() {
        UserDetails userDetails;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userDetails = (UserDetails) authentication.getPrincipal();
        } catch (NullPointerException nullPointerException) {
            return entityManager;
        }
        if (userDetails.getAuthorities().stream().anyMatch(x-> x.getAuthority().equals("admin"))) {
            log.info("using admin connection");
            return entityManager;
        }
        else if (userDetails.getAuthorities().stream().anyMatch(x-> x.getAuthority().equals("hr"))) {
            log.info("using hr connection");
            return entityManagerHr;
        }
        else  {
            log.info("using user connection");
            return entityManagerUser;
        }
    }

    public void setClazz( Class< T > clazzToSet ) {

        this.clazz = clazzToSet;
    }
    public T findOne(T2 id ){

        return getUserEntityManager().find(clazz, id);
    }
    public List< T > findAll(){
        try {
            return getUserEntityManager().createQuery("from " + clazz.getName()).getResultList();
        }
        catch (NoResultException noResultException) {
            return null;
        }
    }
    public void save( T entity ){
        try {
            getUserEntityManager().merge(entity);
        }
        catch (RuntimeException e){
            log.error(e.getMessage());
        }
    }
    public void update( T entity ){

        getUserEntityManager().persist( entity );
    }
    public void delete( T entity ){

        getUserEntityManager().remove(entity);
    }
    public void deleteById( T2 entityId ){
        T entity = findOne( entityId );
        delete( entity );
    }
}
