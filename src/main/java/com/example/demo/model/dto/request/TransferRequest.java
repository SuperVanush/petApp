package com.example.demo.model.dto.request;

import com.example.demo.service.TypeAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferRequest {

    private String fromUserLogin;
    private String fromBillName;
    private String toUserLogin;
    private String toBillName;
    private BigDecimal sumTransfer;
    private TypeAction typeAction;
}
