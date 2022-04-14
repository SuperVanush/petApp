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
            IntResult streamResult = listResult.stream().max((o1, o2) -> o1.getId() - o2.getId()).get();
            int maxId = streamResult.getId() + 1;
            intResult.setId(maxId);
        }
        listResult.add(intResult);
        return intResult.getId();
    }

    @Override
    public IntResult findById(int idReturn) {
        IntResult resultInList = listResult.stream()
                .filter(o -> o.getId() == idReturn)
                .findAny()
                .orElseThrow(() -> new CalculatorListException("Result not found"));
        return resultInList;
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
