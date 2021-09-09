package com.example.demo.calc;

import java.util.Scanner;

public class Converter implements Calculate{
    Scanner in = new Scanner(System.in);

    public void startAppConverter() {
        String select;
        do {
            System.out.println("Input value in rubles  ");
            double value = in.nextInt();
            System.out.println("Select currency (d/e/y)  ");
            String currency = in.next();

            if (currency.equals("d")) {
                System.out.println("Value in dollars is =" + exchangeDollar(value));
            }
            if (currency.equals("e")) {

                System.out.println("Value in evro is =" + exchangeEvro(value));
            }
            if (currency.equals("y")) {

                System.out.println("Value in yens is =  " + exchangeYens(value));
            }

            System.out.println("Do you want convert another value? (y/n) ");
            select = in.next();
        }
        while (select.equals("y"));
    }

    private double exchangeDollar(double value) {
        return value * 73.76;
    }

    private double exchangeEvro(double value) {
        return value * 86.80;
    }

    private double exchangeYens(double value) {
        return value * 0.67;
    }

    @Override
    public void foo() {
        System.out.println("Covnerter");
    }
}