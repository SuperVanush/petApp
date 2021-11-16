package com.example.demo.dao;

import com.example.demo.exception.UserListException;
import com.example.demo.model.User;

import java.util.List;

public interface Storage<T> {

  void add(T t);

   void remove(int id);

    void printAll();

    List<T> getListOfElements();

}
