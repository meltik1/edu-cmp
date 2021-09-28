package com.edu_netcracker.cmp.entities;

import com.edu_netcracker.cmp.entities.users.User;

import java.util.List;

public class TestDao extends AbstractJpaDao<User, Long>  {

    @Override
    public User findOne(Long id) {

        return super.findOne(id);
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    public void save(User entity) {
        super.save(entity);
    }

    @Override
    public void update(User entity) {
        super.update(entity);
    }

    @Override
    public void delete(User entity) {
        super.delete(entity);
    }

    @Override
    public void deleteById(Long entityId) {
        super.deleteById(entityId);
    }
}
