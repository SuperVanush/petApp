package com.example.demo.service;

import com.example.demo.model.User;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class UserComparison {

    private Comparator<User> sortByNumber() {
        Comparator<User> sortByNumberComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getId() - o2.getId();
            }
        };
        return sortByNumberComparator;
    }

    private Comparator<User> sortByName() {
        Comparator<User> sortByNameComparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
        return sortByNameComparator;
    }

    public Map<String, Comparator<User>> getComparatorMap() {
        Map<String, Comparator<User>> sorByMap = new HashMap<>();

        sorByMap.put("sortByNumber", sortByNumber());
        sorByMap.put("sortByName", sortByName());
        return sorByMap;
    }
}