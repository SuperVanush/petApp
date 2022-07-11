package com.example.demo.dao;

import java.util.List;

public interface StorageBill<Bill> {

    Bill add(Bill bill);

    List<Bill> getListOfElements();
}
