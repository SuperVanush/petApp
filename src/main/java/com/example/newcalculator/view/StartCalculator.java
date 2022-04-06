package com.example.newcalculator.view;

import com.example.newcalculator.service.ServiceCalculator;

import java.util.List;
import java.util.Scanner;

public class StartCalculator {
    ServiceCalculator serviceCalculator = new ServiceCalculator();

    public static final Scanner in = new Scanner(System.in);

    public void startCalculator() {
        System.out.println("Calculator");
        firstAction();
        printListResult();
    }

    public void firstAction() {
        System.out.println("Input number");
        int firstOperand = in.nextInt();
        System.out.println("Input action");
        String action = in.next();
        System.out.println("Input next number");
        int secondOperand = in.nextInt();
        int firstActionResult = serviceCalculator.mathCalculation(firstOperand, secondOperand, action);
        System.out.println(firstActionResult);
        nextAction();
    }

    public void nextAction() {
        int nextOperand;
        String nextAction;
        do {
            System.out.println("Input action or 'q' for Exit");
            nextAction = in.next();
            if (!nextAction.equals("q")) {
                System.out.println("Input next number");
                nextOperand = in.nextInt();
                int actionResult = serviceCalculator.mathCalculation(nextAction, nextOperand);
                System.out.println(("Result =" + actionResult));
            }
        }
        while (!nextAction.equals("q"));
        System.out.println("EXIT");
    }

    public void printListResult() {
        List<Integer> listResultForPrint = serviceCalculator.resultForPrint();
        System.out.println(listResultForPrint);
    }
}
