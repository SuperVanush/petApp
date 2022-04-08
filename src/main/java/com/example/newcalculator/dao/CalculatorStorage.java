package com.example.newcalculator.dao;

import com.example.demo.dao.Storage;
import com.example.newcalculator.exсeption.CalculatorListException;
import com.example.newcalculator.model.IntResult;

import java.util.ArrayList;
import java.util.List;


public class CalculatorStorage implements Storage<IntResult> {
    private final List<IntResult> listResult = new ArrayList<>();

    @Override
    public int add(IntResult intResult) {
        if (listResult.isEmpty()) {
            intResult.setId(1);
        } else {
            int maxId = intResult.getId();
            for (IntResult resultInList : listResult) {
                int maxNextId = resultInList.getId();
                if (maxNextId > maxId)
                    maxId = maxNextId;
                intResult.setId(maxId + 1);
            }
        }
        listResult.add(intResult);
        return intResult.getId();
    }

    @Override
    public IntResult findById(int idLastReturn) {
        int idLastResult = 0;
        for (IntResult resultInList : listResult) {
            if (resultInList.getId() == idLastResult)
                resultInList = listResult.get(idLastResult);
            return resultInList;
        }
        throw new CalculatorListException("Result not found");
    }

    @Override
    public void remove(int id) {
    }

    @Override
    public void printAll() {
        listResult.forEach(System.out::println);
    }

    @Override
    public List<IntResult> getListOfElements() {
        return listResult;
    }
}
