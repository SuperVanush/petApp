package com.example.demo.dao.impl;

import com.example.demo.dao.StorageUser;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStorage implements StorageUser {

    private final EntityManager entityManager;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public User add(User user) {

        user.setUsername(user.getUsername());
        user.setLogin(user.getLogin());
        user.setPassword(user.getPassword());
        entityManager.persist(user);

        return user;
    }

    @Override
    @Transactional
    public User findById(int id) {
        return userRepository.findById(id)
                .stream()
                .findFirst()
                .get();
    }

    @Override
    @Transactional
    public Optional<User> findByLogin(String login) {
             return entityManager.createNamedQuery("User.findByLogin", User.class).getResultStream().findAny();
    }

    @Override
    public List<User> getListOfElements() {
        return entityManager.createNamedQuery("User.getListOfElements", User.class).getResultList();
    }

    @Override
    public void remove(User user) {
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }
}