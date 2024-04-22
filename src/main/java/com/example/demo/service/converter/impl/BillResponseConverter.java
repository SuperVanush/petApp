package com.example.demo.service.converter.impl;

import com.example.demo.model.Bill;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.service.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BillResponseConverter implements Converter<Bill, BillResponse> {


    @Override
    public BillResponse convert(Bill source) {
        return BillResponse.builder()
                .message("Success")
                .userName(source.getUser().getUserName())
                .billName(source.getBillName())
                .balance(source.getBalance())
                .build();
    }
}
