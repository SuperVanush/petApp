package com.example.demo.factory;

import com.example.demo.dao.BillStorage;
import com.example.demo.dao.Storage;
import com.example.demo.dao.UserStorage;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.BillService;
import com.example.demo.service.SortService;
import com.example.demo.service.UserService;

public class Factory {
    private Factory() {
    }

    private static Storage<User> userStorageInstance;
    private static Storage<Bill> billStorageInstance;
    private static UserService userServiceInstance;
    private static SortService sortServiceInstance;
    private static BillService billServiceInstance;


    public static UserStorage getUserStorageInstance() {
        if (userStorageInstance == null) {
            userStorageInstance = new UserStorage();
        }
        return (UserStorage) userStorageInstance;
    }

    public static BillStorage getBillStorageInstance() {
        if (billStorageInstance == null) {
            billStorageInstance = new BillStorage();
        }
        return (BillStorage) billStorageInstance;
    }

    public static UserService getUserServiceInstance() {
        if (userServiceInstance == null) {
            userServiceInstance = new UserService();
        }
        return userServiceInstance;
    }

    public static SortService getSortServiceInstance() {
        if (sortServiceInstance == null) {
            sortServiceInstance = new SortService();
        }
        return sortServiceInstance;
    }

    public static BillService getBillServiceInstance() {
        if (billServiceInstance ==null) {
            billServiceInstance= new BillService();
        }
        return billServiceInstance;
    }
}

