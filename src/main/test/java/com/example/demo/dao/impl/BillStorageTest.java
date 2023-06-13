package com.example.demo.dao.impl;

//@RunWith(MockitoJUnitRunner.class)
//public class BillStorageTest extends TestCase {
//    BillStorage subj;
//    UserStorage userStorage;
//
//    @Before
//    public void setUp() throws Exception {
//        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
//        System.setProperty("jdbcUserName", "sa");
//        System.setProperty("jdbcPassword", "");
//        ApplicationContext context = new AnnotationConfigApplicationContext();
//        subj = context.getBean(BillStorage.class);
//        userStorage = context.getBean(UserStorage.class);
//    }
//
//    @Test
//    public void testAddBill() {
//        User user = User.builder().username("qqq").login("qqq").build();
//
//        Bill bill = Bill.builder().name("bill_qqq")
//                .balance(BigDecimal.valueOf(55)).build();
//
//        User addedUser = userStorage.add(user);
//        bill = Bill.builder().user(addedUser).build();
//
//        Bill addedBill = subj.add(bill);
//        Bill billFromBd = subj.findBillFromId(addedBill.getId());
//
//        assertEquals(addedBill, billFromBd);
//    }
//
//    @Test
//    public void testGetListOfElements() {
//        User user1 = User.builder().username("user1").login("loginUser1").build();
//
//        Bill bill1 = Bill.builder().name("billUser1")
//                .balance(BigDecimal.valueOf(789)).build();
//
//        User addedUser1 = userStorage.add(user1);
//        bill1 = Bill.builder().user(addedUser1).build();
//
//        List<Bill> testBillList = new ArrayList<>();
//        testBillList.add(bill1);
//
//        subj.add(bill1);
//        List<Bill> listFromBD = subj.getListOfElements();
//        assertEquals(testBillList, listFromBD);
//    }
//
//    @Test
//    public void testFindBillFromId() {
//        User user1 = User.builder().username("User1").login("LoginUser1").build();
//
//        Bill bill1 = Bill.builder().name("BillUser1")
//                .balance(BigDecimal.valueOf(55)).build();
//
//        User user2 = User.builder().username("User2").login("LoginUser2").build();
//
//        Bill bill2 = Bill.builder().name("BillUser2")
//                .balance(BigDecimal.valueOf(999)).build();
//
//        User user3 = User.builder().username("User3").login("LoginUser3").build();
//
//        Bill bill3 = Bill.builder().name("BillUser3")
//                .balance(BigDecimal.valueOf(777)).build();
//
//        User addedUser1 = userStorage.add(user1);
//        bill1 = Bill.builder().user(addedUser1).build();
//        User addedUser2 = userStorage.add(user2);
//        bill2= Bill.builder().user(addedUser2).build();
//        User addedUser3 = userStorage.add(user3);
//        bill3 = Bill.builder().user(addedUser3).build();
//
//        Bill addedBill1 = subj.add(bill1);
//        Bill addedBill2 = subj.add(bill2);
//        Bill addedBill3 = subj.add(bill3);
//        Bill billFromBd1 = subj.findBillFromId(addedBill1.getId());
//        Bill billFromBd2 = subj.findBillFromId(addedBill2.getId());
//        Bill billFromBd3 = subj.findBillFromId(addedBill3.getId());
//
//        assertEquals(addedBill3, billFromBd3);
//    }
//}