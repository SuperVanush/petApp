package com.example.demo.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BillDtoResponse {

    private String billName;
    private BigDecimal balance;
    private int userId;
}
