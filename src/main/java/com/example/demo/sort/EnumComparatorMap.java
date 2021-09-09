package com.example.demo.sort;

public enum EnumComparatorMap {

    SORTBYNUMBER("sortByNumber"), SORTBYNAME("sortByName");

    private final String value;

    EnumComparatorMap(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
