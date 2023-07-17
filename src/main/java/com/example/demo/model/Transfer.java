package com.example.demo.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@NamedQueries({
        @NamedQuery(
                name = "Transfer.getListOfElements",
                query = "select a from Transfer a")
})
@Table(name = "transaction_history")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;

    @Column(name = "user_from_id")
    private int idFromUser;
    @Column(name = "user_to_id")
    private int idToUser;
    @Column(name = "bill_from_id")
    private int idFromBill;
    @Column(name = "bill_to_id")
    private int idToBill;
    @Column(name = "sum_transaction")
    private BigDecimal sumTransaction;
    @Column(name = "time_date_transaction")
    private LocalDateTime timeDateTransaction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return id == transfer.id && Objects.equals(idFromBill, transfer.idFromBill) && Objects.equals(idToUser, transfer.idToUser) && (Objects.equals(idToBill, transfer.idToBill) && (Objects.equals(sumTransaction, transfer.sumTransaction) && (Objects.equals(timeDateTransaction, transfer.timeDateTransaction))));
    }
}