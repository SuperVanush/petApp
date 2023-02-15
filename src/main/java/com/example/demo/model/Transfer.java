package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transfer {

    private int id;
    private int idFromUser;
    private int idToUser;
    private int idFromBill;
    private int idToBill;
    private int sumTransaction;
    private LocalDateTime timeDateTransaction;

    public Transfer(int idFromUser, int idFromBill,
                    int idToUser, int idToBill, int sumTransaction, LocalDateTime timeDateTransaction) {
        this.idFromUser = idFromUser;
        this.idToUser = idToUser;
        this.idFromBill = idFromBill;
        this.idToBill = idToBill;
        this.sumTransaction = sumTransaction;
        this.timeDateTransaction = timeDateTransaction;
    }

    public Transfer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFromUser() {
        return idFromUser;
    }

    public void setIdFromUser(int idFromUser) {
        this.idFromUser = idFromUser;
    }

    public int getIdToUser() {
        return idToUser;
    }

    public void setIdToUser(int idToUser) {
        this.idToUser = idToUser;
    }

    public int getIdFromBill() {
        return idFromBill;
    }

    public void setIdFromBill(int idFromBill) {
        this.idFromBill = idFromBill;
    }

    public int getIdToBill() {
        return idToBill;
    }

    public void setIdToBill(int idToBill) {
        this.idToBill = idToBill;
    }

    public int getSumTransaction() {
        return sumTransaction;
    }

    public void setSumTransaction(int sumTransaction) {
        this.sumTransaction = sumTransaction;
    }

    public LocalDateTime getTimeDateTransaction() {
        return timeDateTransaction;
    }

    public void setTimeDateTransaction(LocalDateTime timeDateTransaction) {
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

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", idFromUser=" + idFromUser +
                ", idToUser=" + idToUser +
                ", idFromBill=" + idFromBill +
                ", idToBill=" + idToBill +
                ", sumTransaction=" + sumTransaction +
                ", timeDateTransaction=" + timeDateTransaction +
                '}';
    }
}