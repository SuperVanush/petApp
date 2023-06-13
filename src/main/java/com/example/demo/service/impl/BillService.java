package com.example.demo.service.impl;

@Service
@AllArgsConstructor
public class BillService implements ServiceBill {

    private final StorageBill billStorage;

    @Override
    public void addBill(String billName, BigDecimal billBalance, User user) {
   //     Bill bill = Bill.builder()
    //            .name(billName)
     //           .balance(billBalance).user(user).build();
    //    billStorage.add(bill);
    }

    @Override
    public List<Bill> findBillsByUser(User findUser) {
        List<Bill> billList = billStorage.getListOfElements();
   //     List<Bill> billsList = billList
     //           .stream()
       //         .filter(bill -> findUser.getId() == bill.getUser().getId())
     //           .collect(Collectors.toList());

   //     if (billsList.isEmpty()) {
            throw new MyExceptionBill("This user doesn't have bills");
        }
    //    return billsList;
   // }
}