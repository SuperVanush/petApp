package com.example.demo;

import com.example.demo.model.Bill;
import com.example.demo.model.User;
import java.math.BigDecimal;

public class TestData {

  public static Bill createBillWithUser(User user) {
    return Bill.builder().user(user).billName("testBill" + randomInt()).balance(BigDecimal.valueOf(randomInt())).build();
  }

  public static Bill createBill() {
    return Bill.builder().billName("testBill" + randomInt()).balance(BigDecimal.valueOf(randomInt())).build();
  }

  public static User createUser() {
    return User.builder()
        .login("login")
        .id(randomInt())
        .build();
  }

  public static int randomInt() {
    return (int) ((Math.random() * (10000 - 1)) + 1);
  }
}
