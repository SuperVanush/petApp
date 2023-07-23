package com.example.demo.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferFromUserToUserResponse {

    private String message;
    private String fromUserName;
    private String toUserName;
    private String fromBillName;
    private String toBillName;
    private BigDecimal fromBillBalance;
    private BigDecimal toBillBalance;

}
