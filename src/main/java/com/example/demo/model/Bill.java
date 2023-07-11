package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@NamedQueries({
        @NamedQuery(
                name = "Bill.findById",
                query = "select a from Bill a where a.id=:id"),
        @NamedQuery(
                name = "Bill.updateBill",
                query = "update Bill a set a.balance =:balance where a.id = :id"),
        @NamedQuery(
                name = "Bill.getListOfElements",
                query = "select a from Bill a")
})
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private int id;

    @Column(name = "bill_name")
    private String billName;
    @Column(name = "bill_balance")
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return id == bill.id && Objects.equals(billName, bill.billName) && Objects.equals(balance, bill.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, billName, balance);
    }
}