package com.example.demo.dao.impl;

import com.example.demo.dao.StorageUser;
import com.example.demo.model.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserStorage implements StorageUser {

    private final EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;

    @Override
    public User add(User user) {
        var params = new MapSqlParameterSource();
        params.addValue("user_name", user.getUsername());
        params.addValue("login", user.getLogin());
        params.addValue("password", user.getPassword());
        jdbcTemplate.update("insert into users (user_name, login, password) VALUES (?,?,?)","user_name","login","password");
        return user;
    }

    @Override
    public User findById(int id) {
        return entityManager.createNamedQuery("User.findById", User.class)
                .setParameter("id", id).getResultList().stream().findFirst().get();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return entityManager.createNamedQuery("User.findByLogin", User.class)
                .setParameter("login", login).getResultList().stream().findFirst();
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