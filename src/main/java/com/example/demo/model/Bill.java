package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {

    private String name;
    private int id;
    private BigDecimal balance;
    private User user;

    public Bill(String name, int id, BigDecimal balance) {
        this.name = name;
        this.id = id;
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return id == bill.id && Objects.equals(name, bill.name) &&
                Objects.equals(balance, bill.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance);
    }
}