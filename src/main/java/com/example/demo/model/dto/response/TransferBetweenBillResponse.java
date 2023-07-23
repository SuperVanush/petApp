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
public class TransferBetweenBillResponse {

    String message;
    String fromBillName;
    BigDecimal fromBillBalance;
    String toBillName;
    BigDecimal toBillBalance;
}
