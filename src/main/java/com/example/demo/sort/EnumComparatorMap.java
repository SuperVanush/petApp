package com.example.demo.sort;

public enum EnumComparatorMap {

    SORT_BY_NUMBER("sortByNumber"), SORT_BY_NAME("sortByName");

    private final String value;

    EnumComparatorMap(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
