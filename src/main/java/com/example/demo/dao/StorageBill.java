package com.example.demo.dao;

import com.example.demo.model.Bill;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StorageBill {

    Bill add(Bill bill);

    List<Bill> getListOfElements();

    Bill findBillFromId(int idBill);

    void updateBill(Bill bill);
}