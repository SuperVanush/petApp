package com.example.demo.factory;

import com.example.demo.dao.DaoConfiguration;
import com.example.demo.dao.StorageBill;
import com.example.demo.dao.StorageUser;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import com.example.demo.view.BillMenu;
import com.example.demo.view.UserMenu;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan
@Import(DaoConfiguration.class)
@Configuration
public class ServiceConfiguration {

    private static StorageUser userStorageInstance;
    private static StorageBill billStorageInstance;
    private static UserService userServiceInstance;
    private static BillService billServiceInstance;
    private static BillMenu billMenuInstance;
    private static UserMenu userMenuInstance;

    private ServiceConfiguration() {
    }

  /*  public static StorageUser getUserStorageInstance() {
        if (userStorageInstance == null) {
            userStorageInstance = new UserStorage();
        }
        return userStorageInstance;
    }

    public static StorageBill getBillStorageInstance() {
        if (billStorageInstance == null) {
            billStorageInstance = new BillStorage();
        }
        return billStorageInstance;
    }

    public static UserService getUserServiceInstance() {
        if (userServiceInstance == null) {
            userServiceInstance = new UserService(getUserStorageInstance(), getBillServiceInstance());
        }
        return userServiceInstance;
    }

    public static BillService getBillServiceInstance() {
        if (billServiceInstance == null) {
            billServiceInstance = new BillService(getBillStorageInstance());
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
*/}