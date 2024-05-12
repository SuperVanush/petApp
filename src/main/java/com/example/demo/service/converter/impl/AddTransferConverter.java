package com.example.demo.service.converter.impl;

import com.example.demo.model.Transfer;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.service.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AddTransferConverter implements Converter<TransferRequest, Transfer> {

    @Override
    public Transfer convert(TransferRequest source) {
        return Transfer.builder()
                .localDateTime(LocalDateTime.now())
                .build();
    }
}
