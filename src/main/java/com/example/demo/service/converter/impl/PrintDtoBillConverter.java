package com.example.demo.service.converter.impl;

import com.example.demo.model.Bill;
import com.example.demo.model.dto.response.PrintBillDto;
import com.example.demo.service.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PrintDtoBillConverter implements Converter<Bill, PrintBillDto> {

    @Override
    public PrintBillDto convert(Bill source) {
        return PrintBillDto.builder()
                .userId(source.getUser().getId())
                .billName(source.getBillName())
                .balance(source.getBalance())
                .build();
    }
}
