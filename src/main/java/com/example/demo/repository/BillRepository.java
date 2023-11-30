package com.example.demo.repository;


import com.example.demo.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    Optional<Bill> findBillById(int id);

    }
