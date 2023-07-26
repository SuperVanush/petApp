package com.example.demo.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransferResponse {

    private String message;
    private String fromUserName;
    private String toUserName;
    private String fromBillName;
    private String toBillName;
    private BigDecimal fromBillBalance;
    private BigDecimal toBillBalance;


}
