package com.example.demo.dao;
import com.example.demo.exception.BillListException;
import com.example.demo.model.Bill;
import java.util.ArrayList;
import java.util.List;

public class BillStorage implements Storage<Bill> {
    private final List<Bill> billList = new ArrayList<>();


    @Override
    public int add(Bill bill) {
        if (billList.isEmpty()) {
            bill.setId(1);
        } else {
            int maxId = billList.get(0).getId();
            for (Bill billInList : billList) {
                int maxNextId = billInList.getId();
                if (maxNextId > maxId)
                    maxId = maxNextId;
                bill.setId(maxId + 1);
            }
        }
        billList.add(bill);
        int Id = bill.getId();
        return Id;
    }

    @Override
    public Bill findById(int id) throws BillListException {
        for (Bill billInList : billList) {
            if (billInList.getId() == id) {
                return billInList;
            }
        }
        throw new BillListException("Bill is not found");
    }

    @Override
    public void remove(int id) throws BillListException {

    }

    @Override
    public void printAll() {

    }

    @Override
    public List<Bill> getListOfElements() {
        return billList;
    }

}

