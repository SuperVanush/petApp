package com.example.demo.dao.impl;

import com.example.demo.model.User;
import com.example.demo.view.ViewConfig;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserStorageTest extends TestCase {
    UserStorage subj;
    BillStorage billStorage;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUserName", "sa");
        System.setProperty("jdbcPassword", "");
        ApplicationContext context = new AnnotationConfigApplicationContext(ViewConfig.class);
        subj = context.getBean(UserStorage.class);
        billStorage = context.getBean(BillStorage.class);
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setLogin("User1");
        user.setName("User1");

        subj.add(user);
        User returnUser = subj.findById(user.getId());
        assertEquals(user, returnUser);
    }

    @Test
    public void testFindById() {
        User user = new User();
        user.setId(4);
        user.setName("Test_User_Name");
        user.setLogin("Test_User_Login");

        subj.add(user);
        User findUser = subj.findById(user.getId());
        assertEquals(user.getName(), findUser.getName());
    }

    @Test
    public void testFindByLogin() {
        User user = new User();
        user.setId(10);
        user.setName("TestUserName");
        user.setLogin("TestUserLogin");

        subj.add(user);
        User returnUser = subj.findByLogin(user.getLogin());
        assertEquals(user.getId(), returnUser.getId());
    }

    @Test
    public void findByPassword() {
        User user = new User();
        user.setId(10);
        user.setName("TestUserName");
        user.setLogin("TestUserPassword");
        user.setPassword("TestUserPassword");

        subj.add(user);
        User returnUser = subj.findByPassword(user.getPassword());
        assertEquals(user.getId(), returnUser.getId());
    }

    @Test
    public void testGetListOfElements() {
        User firstUser = new User();
        firstUser.setId(13);
        firstUser.setName("FirstUserName");
        firstUser.setLogin("FirstUserLogin");

        User secondUser = new User();
        secondUser.setId(20);
        secondUser.setName("SecondUserName");
        secondUser.setLogin("SecondUserLogin");

        List<User> userList = new ArrayList<>();
        userList.add(firstUser);
        userList.add(secondUser);

        subj.add(firstUser);
        subj.add(secondUser);
        List<User> listUserToCompare = subj.getListOfElements();
        assertEquals(userList, listUserToCompare);
    }

    @Test
    public void testRemoveUser() {
        User firstUser = new User();
        firstUser.setId(5);
        firstUser.setName("FirstUserName");
        firstUser.setLogin("FirstUserLogin");

        User secondUser = new User();
        secondUser.setId(12);
        secondUser.setName("SecondUserName");
        secondUser.setLogin("SecondUserLogin");

        User thirdUser = new User();
        thirdUser.setId(9);
        thirdUser.setName("ThirdUserName");
        thirdUser.setLogin("ThirdUserLogin");

        subj.add(firstUser);
        subj.add(secondUser);
        subj.add(thirdUser);

        List<User> userList = new ArrayList<>();
        userList.add(firstUser);
        userList.add(thirdUser);

        subj.remove(secondUser.getId());

        List<User> returnListUser = subj.getListOfElements();
        assertEquals(userList.size(), returnListUser.size());
    }
}
