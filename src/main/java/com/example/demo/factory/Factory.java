package com.example.demo.factory;

import com.example.demo.dao.Storage;
import com.example.demo.dao.UserStorage;
import com.example.demo.model.User;

public class Factory {
    private Factory() {
    }

    private static Storage<User> userStorageInstance;

    public static UserStorage getUserStorageInstance() {
        if (userStorageInstance == null) {
            userStorageInstance = new UserStorage();
        }
        return (UserStorage) userStorageInstance;
    }
}
