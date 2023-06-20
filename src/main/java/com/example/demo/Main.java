package com.example.demo;

import com.example.demo.model.JpaConfiguration;
import com.example.demo.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JpaConfiguration.class);
        EntityManager entityManager= context.getBean(EntityManager.class);

        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        User user = new User();
        user.setUsername("Lev");
        user.setLogin("LLL");
        user.setPassword("lll");
        entityManager.persist(user);

        entityTransaction.commit();
    }
}