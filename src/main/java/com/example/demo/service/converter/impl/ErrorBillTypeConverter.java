package com.example.demo.service.converter.impl;

import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.types.TypeBill;
import com.example.demo.service.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ErrorBillTypeConverter implements Converter<TypeBill, PrintTransferResponse> {

    @Override
    public PrintTransferResponse convert(TypeBill source) {
        return PrintTransferResponse.builder()
                .message("Неверный тип счета")
                .build();
    }
}
