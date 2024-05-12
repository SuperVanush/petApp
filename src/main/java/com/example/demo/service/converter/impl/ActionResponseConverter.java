package com.example.demo.service.converter.impl;

import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.service.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ActionResponseConverter implements Converter<TransferRequest, TransferResponse> {


    @Override
    public TransferResponse convert(TransferRequest source) {
        return TransferResponse.builder()
                .message("Неверный тип действия")
                .build();
    }
}
