package com.example.demo.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BillDto {

    private int id;
    private String billName;
    private BigDecimal balance;
    private int userId;
}
