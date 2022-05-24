package com.example.demo.factory;

import com.example.demo.dao.BillStorage;
import com.example.demo.dao.Storage;
import com.example.demo.dao.UserStorage;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.BillService;
import com.example.demo.service.UserService;


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

    private static Storage<Bill> billStorageInstance;

    public static BillStorage getBillStorageInstance() {
        if (billStorageInstance == null) {
            billStorageInstance = new BillStorage();
        }
        return (BillStorage) billStorageInstance;
    }

    public static UserService userServiceinstance;

    public static UserService getUserServiceInstance() {
        if (userServiceinstance == null) {
            userServiceinstance = new UserService();
        }
        return (UserService) userServiceinstance;
    }

    public static BillService billServiceinstance;

    public static BillService getBillServiceInstance() {
        if (billServiceinstance == null) {
            billServiceinstance = new BillService();
        }
        return (BillService) billServiceinstance;
    }
}
