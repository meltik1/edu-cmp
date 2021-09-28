package com.edu_netcracker.cmp.entities;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<T extends Serializable, T2 extends Serializable> {
    T findOne(final T2 id);
    List<T> findAll();
    void create(final T entity);
    T update(final T entity);
    void delete(final T entity);
    void deleteById(final T2 entityId);
}
