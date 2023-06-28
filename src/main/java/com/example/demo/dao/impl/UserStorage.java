package com.example.demo.dao.impl;

import com.example.demo.dao.StorageUser;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStorage implements StorageUser {

    private final EntityManager entityManager;

    @Override
    public User add(User user) {
        entityManager.getTransaction().begin();

        user.setUsername(user.getUsername());
        user.setLogin(user.getLogin());
        user.setPassword(user.getPassword());
        entityManager.persist(user);
        entityManager.getTransaction().commit();
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