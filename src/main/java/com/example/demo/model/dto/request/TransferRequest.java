package com.example.demo.model.dto.request;

import com.example.demo.model.types.TypeAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {

    private UUID fromBillId;
    private UUID toBillId;
    private BigDecimal sumTransfer;
    private TypeAction typeAction;
}
