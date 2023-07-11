package com.example.demo.service.impl;

import com.example.demo.dao.impl.BillStorage;
import com.example.demo.exception.MyExceptionBill;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.ServiceBill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService implements ServiceBill {

    private final BillStorage billStorage;

    @Override
    public void addBill(String billName, BigDecimal billBalance, User user) {
        Bill bill = Bill.builder()
                .billName(billName)
                .balance(billBalance).user(user).build();
        billStorage.add(bill);
    }

    @Override
    public List<Bill> findBillsByUser(User findUser) {
        List<Bill> billList = billStorage.getListOfElements();
        List<Bill> billsList = billList
                .stream()
                .filter(bill -> findUser.getId() == bill.getUser().getId())
                .collect(Collectors.toList());

        if (billsList.isEmpty()) {
            throw new MyExceptionBill("This user doesn't have bills");
        }
        return billsList;
    }
}