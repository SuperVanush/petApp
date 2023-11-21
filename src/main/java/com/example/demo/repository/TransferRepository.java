package com.example.demo.repository;


import com.example.demo.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository <Transfer, Integer> {
    List<Transfer> getListOfElements();

}
