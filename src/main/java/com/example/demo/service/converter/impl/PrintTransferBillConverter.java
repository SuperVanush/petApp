package com.example.demo.service.converter.impl;

import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.dto.response.PrintTransferDto;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.repository.TransferRepository;
import com.example.demo.service.converter.Converter;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Data
public class PrintTransferBillConverter implements Converter<Bill, PrintTransferResponse> {
    private final Converter<Transfer, PrintTransferDto> printConverter;
    private final TransferRepository transferRepository;

    @Override
    public PrintTransferResponse convert(Bill source) {
        return PrintTransferResponse.builder()
                .userName(source.getUser().getUserName())
                .billName(source.getBillName())
                .printTransferDtoList(transferRepository.findTransfersByFromBill_id(source.getId())
                        .stream()
                        .map(printConverter::convert)
                        .collect(Collectors.toList()))
                .build();
    }
}
