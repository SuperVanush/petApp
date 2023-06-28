package com.example.demo.dao.impl;

import com.example.demo.dao.StorageUser;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Service

public class UserStorage implements StorageUser {

    EntityManager entityManager;
    EntityTransaction entityTransaction;

    public UserStorage(@Qualifier("createEntityManager") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User add(User user) {
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        user.setUsername(user.getUsername());
        user.setLogin(user.getLogin());
        user.setPassword(user.getPassword());
        entityManager.persist(user);

        return user;
    }

    @Override
    public User findById(int id) {
        return entityManager.createNamedQuery("User.findById", User.class).setParameter(id, id).getSingleResult();
    }

    @Override
    public User findByLogin(String login) {
        return entityManager.createNamedQuery("User.findByLogin", User.class).setParameter("login", login).getSingleResult();
    }

    @Override
    public List<User> getListOfElements() {
        return entityManager.createNamedQuery("User.getListOfElements", User.class).getResultList();
    }

    @Override
    public void remove(int id) {
        int update = entityManager.createNamedQuery("User.remove", User.class).setParameter(id, id).executeUpdate();
    }
}