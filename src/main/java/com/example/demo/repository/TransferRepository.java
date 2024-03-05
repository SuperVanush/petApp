package com.example.demo.repository;


import com.example.demo.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Integer> {

    List<Transfer> findTransfersByFromUser_LoginAndFromBill_BillName (String userLogin, String billName) ;

    List<Transfer> findTransfersByToUser_LoginAndToBill_BillName (String userLogin, String billName) ;
}
