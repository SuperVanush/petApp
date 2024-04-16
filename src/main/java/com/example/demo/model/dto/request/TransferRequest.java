package com.example.demo.model.dto.request;

import com.example.demo.service.emun.TypeAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {

    private String fromUserLogin;
    private String fromBillName;
    private String toUserLogin;
    private String toBillName;
    private BigDecimal sumTransfer;
    private TypeAction typeAction;
}
