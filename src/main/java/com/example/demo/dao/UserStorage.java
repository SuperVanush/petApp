package com.example.demo.dao;
import com.example.demo.exception.UserListException;
import com.example.demo.model.User;
import java.util.ArrayList;
import java.util.List;


public final class UserStorage implements Storage<User> {
    private final List<User> userList = new ArrayList<>();

      @Override
    public User add(User user) {
        if (userList.isEmpty()) {
            user.setId(1);
        } else {
            int maxId = userList.get(0).getId();
            for (User userInList : userList) {
                int maxNextId = userInList.getId();
                if (maxNextId > maxId)
                    maxId = maxNextId;
                user.setId(maxId + 1);
            }
        }
        userList.add(user);
        return user;
    }

    @Override
    public User findById(int id) {
        for (User userInList : userList) {
            if (userInList.getId() == id) {
                return userInList;
            }
        }
        throw new UserListException("User is not found");
    }

    @Override
    public void printAll() {
        userList.forEach(System.out::println);
    }

    @Override
    public List<User> getListOfElements() {
        return userList;
    }

    @Override
    public void remove(int id) throws UserListException {
        int indexOfDeleteUser;
        boolean isUserDeleted = false;
        for (User userInList : userList) {
            if (userInList.getId() == id) {
                indexOfDeleteUser = userList.indexOf(userInList);
                userList.remove(indexOfDeleteUser);
                isUserDeleted = true;
                break;
            }
        }
        if (!isUserDeleted) {
            throw new UserListException("User is not found");
        }
    }
}