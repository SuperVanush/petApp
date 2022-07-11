package com.example.demo.factory;

import com.example.demo.dao.*;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.BillService;
import com.example.demo.service.UserService;

public class Factory {

    private Factory() {
    }

    private static StorageUser<User> userStorageInstance;

    public static UserStorage getUserStorageInstance() {
        if (userStorageInstance == null) {
            userStorageInstance = new UserStorage();
        }
        return (UserStorage) userStorageInstance;
    }

    private static StorageBill<Bill> billStorageInstance;

    public static BillStorage getBillStorageInstance() {
        if (billStorageInstance == null) {
            billStorageInstance = new BillStorage();
        }
        return (BillStorage) billStorageInstance;
    }

    private static UserService userServiceInstance;

    public static UserService getUserServiceInstance() {
        if (userServiceInstance == null) {
            userServiceInstance = new UserService();
        }
        return userServiceInstance;
    }

    private static BillService billServiceInstance;

    public static BillService getBillServiceInstance() {
        if (billServiceInstance == null) {
            billServiceInstance = new BillService();
        }
        return billServiceInstance;
    }
}
