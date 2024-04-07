package com.example.demo.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrintTransferResponse {

    String message;
    String userName;
    String billName;
    List<PrintTransferDto> printTransferDtoList;
}
