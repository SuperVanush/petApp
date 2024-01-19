package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transaction_history")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_from_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_to_id")
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_from_id")
    private Bill fromBill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_to_id")
    private Bill toBill;

    @Column(name = "sum_transaction")
    private BigDecimal sumTransaction;
    @Column(name = "time_date_transaction")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'   'HH:mm:ss")
    private Timestamp timeDateTransaction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return id == transfer.id && Objects.equals(fromBill, transfer.fromBill) && Objects.equals(toUser, transfer.toUser)
                && (Objects.equals(toBill, transfer.toBill) && (Objects.equals(sumTransaction, transfer.sumTransaction)
                && (Objects.equals(timeDateTransaction, transfer.timeDateTransaction))));
    }
}