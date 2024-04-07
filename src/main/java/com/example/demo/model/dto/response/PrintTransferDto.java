package com.example.demo.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrintTransferDto {

    private String fromUserName;
    private String fromBillName;
    private String toUserName;
    private String toBillName;
    private BigDecimal sumTransfer;
    private LocalDateTime localDateTime;
}
