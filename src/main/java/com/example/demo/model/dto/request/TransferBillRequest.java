package com.example.demo.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferBillRequest {

    private String loginUser;
    private int idBill;
    private BigDecimal sumTransfer;

}
