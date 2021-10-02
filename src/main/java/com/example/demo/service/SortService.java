package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.factory.Factory;
import com.example.demo.model.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

public class SortService {

    private final Scanner in = new Scanner(System.in);
    private final UserComparison sortUser = new UserComparison();
    private final Storage<User> userStorage = Factory.getUserStorageInstance();

    public void startSortUsersByName() {

        Map<String, Comparator<User>> getMap = sortUser.getComparatorMap();

                    Collections.sort(userStorage.getListOfElements(), getMap.get(EnumComparatorMap.SORT_BY_NAME.getValue()));
                    System.out.println(userStorage.getListOfElements());
                }
    public void startSortUsersByNumber() {
        Map<String, Comparator<User>> getMap = sortUser.getComparatorMap();
                    Collections.sort(userStorage.getListOfElements(), getMap.get(EnumComparatorMap.SORT_BY_NUMBER.getValue()));
                    System.out.println(userStorage.getListOfElements());
                }

    }
