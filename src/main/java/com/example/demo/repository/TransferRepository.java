package com.example.demo.repository;


import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface  TransferRepository extends JpaRepository<Transfer, Integer> {
    @Query("select t from Transfer t where t.fromBill = ?1")
    List<Transfer> findTransferByFromBill(Bill bill);
}
