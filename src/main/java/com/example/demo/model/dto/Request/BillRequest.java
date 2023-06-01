package com.example.demo.model.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {

    private String billName;
    private String login;
    private BigDecimal balance;
}
