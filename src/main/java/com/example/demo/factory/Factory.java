package com.example.demo.factory;

import com.example.demo.dao.implstorage.BillStorage;
import com.example.demo.dao.StorageBill;
import com.example.demo.dao.StorageUser;
import com.example.demo.dao.implstorage.UserStorage;
import com.example.demo.service.implservice.BillService;
import com.example.demo.service.implservice.UserService;
import com.example.demo.view.BillMenu;
import com.example.demo.view.UserMenu;

public class Factory {

    private static StorageUser userStorageInstance;
    private static StorageBill billStorageInstance;
    private static UserService userServiceInstance;
    private static BillService billServiceInstance;
    private static BillMenu billMenuInstance;
    private static UserMenu userMenuInstance;

    private Factory() {
    }

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

    public static BillService getBillServiceInstance() {
        if (billServiceInstance == null) {
            billServiceInstance = new BillService();
        }
        return billServiceInstance;
    }

    public static BillMenu getBillMenuInstance() {
        if (billMenuInstance == null) {
            billMenuInstance = new BillMenu();
        }
        return billMenuInstance;
    }

    public static UserMenu getUserMenuInstance() {
        if (userMenuInstance == null) {
            userMenuInstance = new UserMenu();
        }
        return userMenuInstance;
    }
}