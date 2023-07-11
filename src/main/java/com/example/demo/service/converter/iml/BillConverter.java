package com.example.demo.service.converter.iml;

import com.example.demo.model.Bill;
import com.example.demo.model.dto.response.BillDto;
import com.example.demo.service.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BillConverter implements Converter<Bill, BillDto> {

  @Override
  public BillDto convert(Bill source) {
    return BillDto.builder()
        .balance(source.getBalance())
        .billName(source.getBillName())
        .userId(source.getUser().getId())
        .build();
  }
}
