package com.example.demo.dao;

import com.example.demo.exception.UserListException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface Storage<T> {

    int add(T t) ;

    T findById (int id);

    void remove(int id) ;

    void printAll();

    List<T> getListOfElements();

    static <T> T of(String classNameForGet) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream("com.example.demo.dao".replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Set<? extends Class<?>> collect = reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(className -> {
                    try {
                        return Class.forName("com.example.demo.dao" + "."
                                + className.substring(0, className.lastIndexOf('.')));
                    } catch (ClassNotFoundException e) {
                        // handle the exception
                    }
                    return null;
                }).collect(Collectors.toSet());

        for (Class<?> aClass : collect) {
            if (aClass.getSimpleName().equals(classNameForGet)) {
                try {
                    return (T) aClass.getDeclaredConstructor().newInstance();
                } catch (InstantiationException
                        | IllegalAccessException
                        | InvocationTargetException
                        | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new RuntimeException("Cannot get class");
    }
}
