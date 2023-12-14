package com.example.demo.model.dto.request;

import com.example.demo.service.RequestType;
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
    private String nameFromBill;
    private String loginToUser;
    private String nameToBill;
    private BigDecimal sumTransfer;
    private BigDecimal fromBillBalance;
    private BigDecimal toBillBalance;
    private RequestType requestType;

}
