package com.example.demo.repositoryTest;


import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.demo.serviceTest.TestData.createBillWithoutId;
import static com.example.demo.serviceTest.TestData.createUserWithoutId;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BillRepositoryTest extends TestCase {

    @Autowired
    BillRepository subj;
    @Autowired
    UserRepository userRepository;

    @Test
    public void findById() {
        User user1 = createUserWithoutId();
        User returnUser1 = userRepository.save(user1);

        Bill bill1 = createBillWithoutId(returnUser1);
        Bill returnBill1 = subj.save(bill1);

        User user2 = createUserWithoutId();
        User returnUser2 = userRepository.save(user2);

        Bill bill2 = createBillWithoutId(user2);
        Bill returnBill2 = subj.save(bill2);

        Bill findBill = subj.findById(returnBill1.getId()).get();

        assertEquals(returnBill1, findBill);
    }
}
