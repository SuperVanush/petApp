package com.example.demo.repository;

import com.example.demo.model.Bill;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    List<Bill> findBillsByUser(User user);

    void deleteBillByUser_UserIdAndAndBillName ( Integer id, String name);
}
