package com.example.demo.repositoryTest;

import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.TransferRepository;
import com.example.demo.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static com.example.demo.serviceTest.TestData.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransferRepositoryTest extends TestCase {

    @Autowired
    TransferRepository subj;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BillRepository billRepository;

    @Test
    public void findTransfersByFromBill_id() {
        User user = createUserWithoutId();
        User returnUser = userRepository.save(user);

        Bill testBill = createBillWithoutId(user);
        Bill returnBill = billRepository.save(testBill);

        BigDecimal sumTransfer1 = BigDecimal.valueOf(createIntRandom());
        Transfer transfer1 = createTransferBetweenBills(returnBill, returnUser, sumTransfer1);
        Transfer transfer2 = createTransferBetweenBills(returnBill, returnUser, sumTransfer1);

        subj.save(transfer1);
        subj.save(transfer2);

        List<Transfer> transferList = subj.findTransfersByFromBill_id(testBill.getId());

        assertEquals(transferList.size(), 2);
    }

    @Test
    public void findTransfersByToBill_id() {
        User user = createUserWithoutId();
        User returnUser = userRepository.save(user);

        Bill testBill = createBillWithoutId(user);
        Bill returnBill = billRepository.save(testBill);

        BigDecimal sumTransfer1 = BigDecimal.valueOf(createIntRandom());
        Transfer transfer1 = createTransferBetweenBills(returnBill, returnUser, sumTransfer1);
        Transfer transfer2 = createTransferBetweenBills(returnBill, returnUser, sumTransfer1);

        subj.save(transfer1);
        subj.save(transfer2);

        List<Transfer> transferList = subj.findTransfersByToBill_Id(testBill.getId());

        assertEquals(transferList.size(), 2);
    }
}
