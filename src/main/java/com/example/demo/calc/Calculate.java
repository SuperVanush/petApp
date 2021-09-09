package com.example.demo.calc;



import java.util.List;

public interface Calculate {

    void foo();

    default void printResult(List<Integer> listResult) {

        System.out.println("Common result: " + listResult.get(listResult.size() - 1));
    }
}
