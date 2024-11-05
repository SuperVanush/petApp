package com.example.demo.service.impl;

import com.example.demo.exception.BalanceException;
import com.example.demo.exception.BillException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.model.dto.response.PrintBillResponse;
import com.example.demo.repository.BillRepository;
import com.example.demo.service.ServiceBill;
import com.example.demo.service.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BillService implements ServiceBill {

    private final UserService userService;
    private final BillRepository billRepository;
    private final Converter<Bill, BillResponse> billResponseConverter;
    private final Converter<User, PrintBillResponse> printBillResponseConverter;

    @Override
    public BillResponse addBill(BillRequest request) {
        User user = userService.findUserById(request.getUserId());
        Bill bill = Bill.builder()
                .billName(request.getBillName())
                .balance(BigDecimal.valueOf(0))
                .user(user)
                .build();
        Bill newBill = billRepository.save(bill);
        return billResponseConverter.convert(newBill);
    }

    @Override
    public PrintBillResponse findBillsByUser(UUID userId) {
        User user = userService.findUserById(userId);
        return printBillResponseConverter.convert(user);
    }

    @Override
    public void deleteBill(UUID billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new BillException("Нет такого счета"));
        billRepository.delete(bill);
    }

    public Bill sumToBillTransfer(Bill toBill, BigDecimal sumTransfer) {
        BigDecimal newBalance = toBill.getBalance().add(sumTransfer);
        toBill.setBalance(newBalance);
        billRepository.save(toBill);
        return toBill;
    }

    public Bill reduceFromBillTransfer(Bill fromBill, BigDecimal sumTransfer) {
        BigDecimal newBalance = fromBill.getBalance().subtract(sumTransfer);
        if (newBalance.compareTo(BigDecimal.ZERO) > 0) {
            fromBill.setBalance(newBalance);
            billRepository.save(fromBill);
        } else {
            throw new BalanceException("Баланс меньше ноля, попробуйте снова");
        }
        return fromBill;
    }

    public Bill findBillById(UUID id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new BillException("Нет такого счета"));
    }
}
