package com.example.demo.service.impl;

//public class TransferServiceTest extends TestCase {
//
//    TransferStorage transferStorage;
//    TransferService subj;
//    BillStorage billStorage;
//
//    @Before
//    public void setUp() throws Exception {
//        billStorage = mock(BillStorage.class);
//        transferStorage = mock(TransferStorage.class);
//        subj = new TransferService(transferStorage, billStorage);
//    }
//
//    @Test
//    public void test_addTransaction_Ok() {
//        Transfer transfer = Transfer.builder()
//                .idFromUser(1).idFromBill(1)
//                .idToUser(2).idToBill(2)
//                .sumTransaction(BigDecimal.valueOf(500))
//                .timeDateTransaction(LocalDateTime.now()).build();
//        transferStorage.add(transfer);
//        verify(transferStorage).add(transfer);
//    }
//
//    @Test
//    public void test_findTransferByBillsId_ok() {
//        Transfer firstTransfer = Transfer.builder().idFromBill(5).build();
//
//        Transfer secondTransfer = Transfer.builder().idFromBill(11).build();
//
//        List<Transfer> listTransfer = new ArrayList<>();
//        listTransfer.add(firstTransfer);
//        listTransfer.add(secondTransfer);
//
//        List<Transfer> listTransferForCompare = new ArrayList<>();
//        listTransferForCompare.add(secondTransfer);
//
//        when(transferStorage.getListOfElements()).thenReturn(listTransfer);
//        List<Transfer> transferListForElevenBill = subj.findTransferByBillsId(11);
//        assertEquals(transferListForElevenBill, listTransferForCompare);
//    }
//
//    @Test
//    public void test_findTransferByBillsId_not_find_transfer() {
//        Bill firstBill = Bill.builder().id(6).build();
//
//        Transfer firstTransfer = Transfer.builder().idFromBill(firstBill.getId()).build();
//
//        Bill secondBill = Bill.builder().id(2).build();
//
//        List<Transfer> firstTransferList = new ArrayList<>();
//        firstTransferList.add(firstTransfer);
//        when(transferStorage.getListOfElements()).thenReturn(firstTransferList);
//
//        List<Transfer> secondTransferList = subj.findTransferByBillsId(secondBill.getId());
//        assertEquals(secondTransferList.size(), 0);
//
//    }
//
//    @Test
//    public void test_sumBalanceTransaction_Ok() {
//        Bill bill = Bill.builder().balance(BigDecimal.valueOf(6)).id(2).build();
//        BigDecimal sumDigit = BigDecimal.valueOf(3);
//        when(billStorage.findBillFromId(6)).thenReturn(bill);
//        Bill returnBill = subj.sumBalanceTransaction(6, BigDecimal.valueOf(3));
//        verify(billStorage).updateBill(returnBill);
//        assertEquals(bill.getBalance(), returnBill.getBalance());
//    }
//
//    @Test
//    public void test_reduceBalance_Ok() {
//        Bill bill = Bill.builder().balance(BigDecimal.valueOf(9)).id(2).build();
//        BigDecimal reduceBalance = BigDecimal.valueOf(2);
//        when(billStorage.findBillFromId(2)).thenReturn(bill);
//        Bill returnBill = subj.reduceBalance(2, reduceBalance);
//        verify(billStorage).updateBill(returnBill);
//        assertEquals(bill.getBalance(), returnBill.getBalance());
//    }
//
//}
