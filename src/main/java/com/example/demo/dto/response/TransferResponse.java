package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {

    private String message;
    private String fromUserName;
    private String fromBillName;
    private String toUserName;
    private String toBillName;
    private BigDecimal FromUserBalance;
    private BigDecimal ToUserBalance;
}
