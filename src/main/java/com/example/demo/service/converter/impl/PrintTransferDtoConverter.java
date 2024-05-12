package com.example.demo.service.converter.impl;

import com.example.demo.model.Transfer;
import com.example.demo.model.dto.response.PrintTransferDto;
import com.example.demo.service.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PrintTransferDtoConverter implements Converter<Transfer, PrintTransferDto> {

    @Override
    public PrintTransferDto convert(Transfer source) {
        return PrintTransferDto.builder()
                .fromUserName(source.getFromUser().getUserName())
                .fromBillName(source.getFromBill().getBillName())
                .toUserName(source.getToUser().getUserName())
                .toBillName(source.getToBill().getBillName())
                .sumTransfer(source.getSumTransfer())
                .localDateTime(source.getLocalDateTime())
                .build();
    }
}
