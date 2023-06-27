package com.example.demo.dao.impl;

import com.example.demo.dao.StorageUser;
import com.example.demo.model.User;
import com.example.demo.service.impl.UserService;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

@Service
@Data
public class UserStorage implements StorageUser {
    private final UserService userService;
    EntityManager entityManager;
    EntityTransaction entityTransaction;

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
        User user = entityManager.createNamedQuery("User.findById", User.class)
                .setParameter(id, Integer.valueOf(id))
                .getSingleResult();

        return user;
    }

    @Override
    public User findByLogin(String login) {
        User user = entityManager.createNamedQuery("User.findByLogin", User.class)
                .setParameter("login", login)
                .getSingleResult();
        return user;
    }

    @Override
    public List<User> getListOfElements() {
        List<User> userList = entityManager.createNamedQuery("User.getListOfElements", User.class)
                .getResultList();
        return userList;
    }

    @Override
    public void remove(int id) {
        int update = entityManager.createNamedQuery("User.remove", User.class)
                .setParameter(id, Integer.valueOf(id))
                .executeUpdate();
    }
}