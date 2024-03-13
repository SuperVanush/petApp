package com.example.demo.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferListResponse {

    private int idFromUser;
    private int idFromBill;
    private int idToUser;
    private int idToBill;
    private BigDecimal sumTransaction;
    private Timestamp timeDateTransaction;
}
