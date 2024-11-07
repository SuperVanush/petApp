package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrintTransferResponse {

    private String message;
    private String userName;
    private String billName;
    private List<PrintTransferDto> printTransferDtoList;
}
