package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    private int id;
    private int idFromUser;
    private int idToUser;
    private int idFromBill;
    private int idToBill;
    private BigDecimal sumTransaction;
    private LocalDateTime timeDateTransaction;

    public Transfer(int idFromUser, int idFromBill,
                    int idToUser, int idToBill, BigDecimal sumTransaction, LocalDateTime timeDateTransaction) {
        this.idFromUser = idFromUser;
        this.idToUser = idToUser;
        this.idFromBill = idFromBill;
        this.idToBill = idToBill;
        this.sumTransaction = sumTransaction;
        this.timeDateTransaction = timeDateTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return id == transfer.id && Objects.equals(idFromBill, transfer.idFromBill) &&
                Objects.equals(idToUser, transfer.idToUser) &&
                Objects.equals(idFromBill, transfer.idFromBill)
                && (Objects.equals(idToBill, transfer.idToBill)
                && (Objects.equals(sumTransaction, transfer.sumTransaction)
                && (Objects.equals(timeDateTransaction, transfer.timeDateTransaction))));
    }
}