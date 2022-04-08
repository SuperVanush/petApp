package com.example.newcalculator.model;

public class IntResult {
    private int id;
    private int value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "IntResult{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
