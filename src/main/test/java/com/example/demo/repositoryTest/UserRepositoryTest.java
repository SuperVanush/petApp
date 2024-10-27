package com.example.demo.repositoryTest;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.demo.serviceTest.TestData.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserRepositoryTest extends TestCase {

    @Autowired
    UserRepository subj;

    @Test
    public void findByLogin_Ok() {
        User user = createUserWithoutId();

        User returnUser = subj.save(user);
        User findUser = subj.findByLogin("Login").get();

        assertEquals(returnUser.getId(), findUser.getId());
        assertEquals(returnUser.getUserName(), findUser.getUserName());
        assertEquals(returnUser.getLogin(), returnUser.getLogin());
    }

    @Test
    public void findById_Ok() {
        User user = createUserWithoutId();

        User returnUser = subj.save(user);
        User findUser = subj.findById(returnUser.getId()).get();

        assertEquals(returnUser.getId(), findUser.getId());
        assertEquals(returnUser.getUserName(), findUser.getUserName());
        assertEquals(returnUser.getLogin(), returnUser.getLogin());
    }
}
