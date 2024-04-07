package com.example.demo.service.impl;

import com.example.demo.exception.BillException;
import com.example.demo.exception.UserException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.model.dto.response.PrintBillDto;
import com.example.demo.model.dto.response.PrintBillResponse;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ServiceBill;
import com.example.demo.service.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService implements ServiceBill {

    private final UserRepository userRepository;
    private final BillRepository billRepository;
    private final Converter<Bill, PrintBillDto> converter;

    @Override
    public BillResponse addBill(BillRequest request) {
        User user = Optional.ofNullable(request)
                .map(BillRequest::getUserId)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new UserException("Пользователь не найден"));
        Bill addBill = Bill.builder()
                .billName(request.getBillName())
                .balance(BigDecimal.valueOf(0))
                .user(user)
                .build();
        billRepository.save(addBill);
        return getSuccessAddBill(addBill);
    }


    @Override
    public PrintBillResponse findBillsByUser(BillRequest request) {
        User user = Optional.ofNullable(request)
                .map(BillRequest::getUserId)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new UserException("Пользователь не найден"));

        return getSuccessPrintBill(user);
    }

    public BillResponse deleteBill(BillRequest request) {
        User user = Optional.ofNullable(request)
                .map(BillRequest::getUserId)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new UserException("Пользователь не найден"));
        Bill bill = user.getListBills()
                .stream()
                .filter(bill1 -> bill1.getBillName().equals(request.getBillName())).findFirst()
                .orElseThrow(() -> new BillException("Нет такого счета"));
        billRepository.delete(bill);
        return getSuccessDeleteBill();
    }

    public BillResponse getSuccessAddBill(Bill bill) {
        String userName = bill.getUser().getUserName();
        return BillResponse.builder()
                .message("Success")
                .userName(userName)
                .billName(bill.getBillName())
                .balance(bill.getBalance())
                .build();
    }

    public BillResponse getSuccessDeleteBill() {
        return BillResponse.builder()
                .message("Success")
                .build();
    }

    public PrintBillResponse getSuccessPrintBill(User user) {
        return PrintBillResponse.builder()
                .userName(user.getUserName())
                .printBillDtoList(getListPrintBill(user))
                .build();
    }

    public List<PrintBillDto> getListPrintBill(User user) {
        return billRepository.findAll()
                .stream()
                .filter(bill -> user.equals(bill.getUser()))
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
