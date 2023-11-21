package com.example.demo.repository;


import com.example.demo.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> getListOfElements();

    Optional<Bill> findBillById(int id);

    Optional<Bill> updateBill(Bill bill);

}
