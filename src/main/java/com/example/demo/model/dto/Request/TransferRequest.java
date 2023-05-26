package com.example.demo.model.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private int idFromUser;
    private int idToUser;
    private int idFromBill;
    private int idToBill;
    private BigDecimal sumTransaction;
    private LocalDateTime timeDateTransaction;
}
