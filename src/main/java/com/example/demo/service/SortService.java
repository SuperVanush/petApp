package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.factory.Factory;
import com.example.demo.model.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SortService {

    private final UserComparison sortUser = new UserComparison();
    private final Storage<User> userStorage = Factory.getUserStorageInstance();

    public List<User> startSortUsersByName() {
        Map<String, Comparator<User>> getMap = sortUser.getComparatorMap();
        Collections.sort(userStorage.getListOfElements(), getMap.get(EnumComparatorMap.SORT_BY_NAME.getValue()));
        return userStorage.getListOfElements();
    }

    public List<User> startSortUsersByNumber() {
        Map<String, Comparator<User>> getMap = sortUser.getComparatorMap();
        Collections.sort(userStorage.getListOfElements(), getMap.get(EnumComparatorMap.SORT_BY_NUMBER.getValue()));
        return userStorage.getListOfElements();
    }
}
