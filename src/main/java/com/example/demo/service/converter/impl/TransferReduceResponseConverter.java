package com.example.demo.service.converter.impl;

import com.example.demo.model.Transfer;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.service.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransferReduceResponseConverter implements Converter<Transfer, TransferResponse> {

    @Override
    public TransferResponse convert(Transfer source) {
        return TransferResponse.builder()
                .message("Success")
                .fromUserName(source.getFromUser().getUserName())
                .fromBillName(source.getFromBill().getBillName())
                .FromUserBalance(source.getFromBill().getBalance())
                .build();
    }
}
