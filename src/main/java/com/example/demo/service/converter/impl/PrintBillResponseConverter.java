package com.example.demo.service.converter.impl;

import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.dto.response.PrintBillDto;
import com.example.demo.dto.response.PrintBillResponse;
import com.example.demo.service.converter.Converter;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Data
@Component
public class PrintBillResponseConverter implements Converter<User, PrintBillResponse> {
    private final Converter<Bill, PrintBillDto> printDtoBillConverter;

    @Override
    public PrintBillResponse convert(User source) {
        return PrintBillResponse.builder()
                .userName(source.getUserName())
                .printBillDtoList(source.getListBills().stream().map(printDtoBillConverter::convert).collect(Collectors.toList()))
                .build();
    }
}
