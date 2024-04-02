package com.example.demo.service.impl;

import com.example.demo.exception.BillNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillDtoResponse;
import com.example.demo.model.dto.response.BillResponse;
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

    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final Converter<Bill, BillDtoResponse> converter;

    public BillResponse addBill(BillRequest request) {
        String billName = request.getBillName();
        BigDecimal billBalance = BigDecimal.valueOf(0);
        User userOfBill = Optional.of(request)
                .map(BillRequest::getLogin)
                .flatMap(userRepository::findByLogin)
                .orElseThrow(() -> new UserNotFoundException("ЭТО ИСКЛЮЧЕНИЕ"));
        Bill bill = Bill.builder()
                .billName(billName)
                .balance(billBalance)
                .user(userOfBill)
                .build();
        billRepository.save(bill);
        return getSuccessAddBillResponse(userOfBill);
    }

    @Override
    public BillResponse findBillsByUser(BillRequest request) {
        return Optional.ofNullable(request)
                .map(BillRequest::getLogin)
                .flatMap(userRepository::findByLogin)
                .map(this::getSuccessFindBillResponse)
                .orElseThrow(() -> new UserNotFoundException("ЭТО ИСКЛЮЧЕНИЕ"));
    }

    @Override
    public Bill findBillByName(String billName) {
        return Optional.ofNullable(billName)
                .flatMap(billRepository::findBillByBillName)
                .orElseThrow(() -> new BillNotFoundException("THIS IS EXCEPTION"));
    }


    private BillResponse getSuccessAddBillResponse(User user) {
        return BillResponse.builder()
                .message("Success")
                .login(user.getLogin())
                .billList(getResponseBills(user))
                .build();
    }

    private BillResponse getSuccessFindBillResponse(User user) {
        return BillResponse.builder()
                .message("Success")
                .login(user.getLogin())
                .billList(getResponseBills(user))
                .build();
    }

    private List<BillDtoResponse> getResponseBills(User user) {
        return billRepository.findAll()
                .stream()
                .filter(bill -> user.equals(bill.getUser()))
                .map(converter::convert)
                .collect(Collectors.toList());
    }
}
