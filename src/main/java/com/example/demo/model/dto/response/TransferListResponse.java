package com.example.demo.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class TransferListResponse {

    private int idFromUser;
    private int idFromBill;
    private int idToUser;
    private int idToBill;
    private BigDecimal sumTransaction;
    private Timestamp timeDateTransaction;
}
