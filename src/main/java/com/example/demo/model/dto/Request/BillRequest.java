package com.example.demo.model.dto.Request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillRequest {
    private String billname;
    private String username;
    private BigDecimal balance;
}
