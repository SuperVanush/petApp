package com.example.demo.service.converter.iml;

import com.example.demo.model.Transfer;
import com.example.demo.model.dto.response.TransferListResponse;
import com.example.demo.service.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TransferConverter implements Converter<Transfer, TransferListResponse> {

    @Override
    public TransferListResponse convert(Transfer source) {
        return TransferListResponse.builder()
                .idFromUser(source.getFromUser().getId())
                .idFromBill(source.getFromBill().getId())
                .idToUser(source.getToUser().getId())
                .idToBill(source.getToBill().getId())
                .sumTransaction(source.getSumTransaction())
                .timeDateTransaction(source.getTimeDateTransaction())
                .build();
    }
}

