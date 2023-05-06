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
        User user = User.builder()
                .login("User1").name("User1").build();

        subj.add(user);
        User returnUser = subj.findById(user.getId());
        assertEquals(user, returnUser);
    }

    @Test
    public void testFindById() {
        User user = User.builder()
                .id(4).name("Test_User_Name")
                .login("Test_User_Login").build();
        subj.add(user);
        User findUser = subj.findById(user.getId());
        assertEquals(user.getName(), findUser.getName());
    }

    @Test
    public void testFindByLogin() {
        User user = User.builder()
                .id(10).name("TestUserName")
                .login("TestUserLogin").build();

        subj.add(user);
        User returnUser = subj.findByLogin(user.getLogin());
        assertEquals(user.getId(), returnUser.getId());
    }

    @Test
    public void testGetListOfElements() {
        User firstUser = User.builder()
                .id(13).name("FirstUserName")
                .login("FirstUserLogin").build();

        User secondUser = User.builder()
                .id(20).name("SecondUserName")
                .login("SecondUserLogin").build();

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
        User firstUser = User.builder()
                .id(5).name("FirstUserName")
                .login("FirstUserLogin").build();

        User secondUser = User.builder()
                .id(12).name("SecondUserName")
                .login("SecondUserLogin").build();

        User thirdUser = User.builder()
                .id(9).name("ThirdUserName")
                .login("ThirdUserLogin").build();

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
