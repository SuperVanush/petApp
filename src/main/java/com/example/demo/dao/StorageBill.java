package com.example.demo.dao;

import com.example.demo.model.Bill;

import java.util.List;

public interface StorageBill {

    Bill add(Bill bill);

    List<Bill> getListOfElements();

    Bill sumBalanceTransaction(int idBill, int sumDigit);

    Bill reduceBalanceTransaction(int idBill, int reduceDigit);
}