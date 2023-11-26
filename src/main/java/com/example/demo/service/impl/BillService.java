package com.example.demo.service.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.repository.BillRepository;
import com.example.demo.service.ServiceBill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService implements ServiceBill {

    private final BillRepository billRepository;

    @Override
    public void addBill(String billName, BigDecimal billBalance, User user) {
        Bill bill = Bill.builder().billName(billName).balance(billBalance).user(user).build();
        billRepository.save(bill);
    }

    @Override
    public List<Bill> findBillsByUser(User findUser) {
        return billRepository
                .findAll()
                .stream()
                .filter(bill -> findUser.getId() == bill.getUser().getId())
                .collect(Collectors.toList());
    }

    @Override
    public Bill findBillById(int id) {
        return billRepository.findBillById(id).orElseThrow(() -> new UserNotFoundException("Bill_Optional is empty"));
    }
}
