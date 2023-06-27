package com.example.demo;

import com.example.demo.model.JpaConfiguration;
import com.example.demo.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JpaConfiguration.class);
        EntityManager entityManager= context.getBean(EntityManager.class);


        User user = entityManager.find(User.class, 10);
        System.out.println(user.getUsername());

    }
}