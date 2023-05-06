package com.example.demo.service.impl;

import com.example.demo.dao.StorageBill;
import com.example.demo.exception.MyExceptionBill;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.ServiceBill;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillService implements ServiceBill {

    private StorageBill billStorage;

    public BillService(StorageBill billStorage) {
        this.billStorage = billStorage;
    }

    @Override
    public void addBill(String billName, BigDecimal billBalance, User user) {
        Bill bill = Bill.builder()
                .name(billName)
                .balance(billBalance).user(user).build();
        billStorage.add(bill);
    }

    @Override
    public List<Bill> findBillsByUser(User findUser) throws MyExceptionBill {
        List<Bill> billsList = new ArrayList<>();
        List<Bill> billList = billStorage.getListOfElements();
        for (Bill billInList : billList) {
            int idUser = findUser.getId();
            if (billInList.getUser().getId() == idUser) {
                billsList.add(billInList);
            }
        }
        if (billsList.isEmpty()) {
            throw new MyExceptionBill("No Bills");
        }
        return billsList;
    }

    public Bill findBillById(int billId) {
        Bill billById = billStorage.findBillFromId(billId);
        return billById;
    }
}
