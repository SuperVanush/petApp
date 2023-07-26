package com.example.demo.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {

    private String loginFromUser;
    private int idFromBill;
    private String loginToUser;
    private int idToBill;
    private BigDecimal sumTransfer;
    private BigDecimal fromBillBalance;
    private BigDecimal toBillBalance;

}
