package com.example.demo.service.converter.iml;

import com.example.demo.model.Bill;
import com.example.demo.model.dto.response.BillDtoResponse;
import com.example.demo.service.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BillConverter implements Converter<Bill, BillDtoResponse> {

    @Override
    public BillDtoResponse convert(Bill source) {
        return BillDtoResponse.builder()
                .balance(source.getBalance())
                .billName(source.getBillName())
                .userId(source.getUser().getId())
                .build();
    }
}
