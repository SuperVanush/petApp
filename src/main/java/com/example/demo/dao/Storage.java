package com.example.demo.dao;

import com.example.demo.exception.UserListException;

import java.util.List;

public interface Storage<T> {

  void add(T t) ;

   // void remove(int i) throws UserListException;

    void printAll();

    List<T> getListOfElements();
}
