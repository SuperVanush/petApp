package com.example.demo.model.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrintBillsRequest {

    private String billName;
    private String login;
    private BigDecimal balance;
}
