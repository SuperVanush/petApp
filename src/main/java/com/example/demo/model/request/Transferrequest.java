package com.example.demo.model.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transferrequest {
    private int idFromUser;
    private int idToUser;
    private int idFromBill;
    private int idToBill;
    private BigDecimal sumTransaction;
    private LocalDateTime timeDateTransaction;
}
