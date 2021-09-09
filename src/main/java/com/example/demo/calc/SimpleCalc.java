package com.example.demo.calc;

import java.util.List;
import java.util.Scanner;

public class SimpleCalc implements Calculate{

    private final Scanner in = new Scanner(System.in);

    public void startApp(List<Integer> listResult) {
        if (!listResult.isEmpty()) {
            printResult(listResult);
            startTwo(listResult, listResult.get(listResult.size() - 1));
        } else {
            System.out.println("Input first operand");
            int firstOperand = in.nextInt();
            startTwo(listResult, firstOperand);
        }
    }

    private void startTwo(List<Integer> listResult, int operand) {
        System.out.println("Input math action");
        String mathAction = in.next();
        int secondOperand;
        int mathResult;

        if (mathAction.equals("+")) {
            System.out.println("Input second operand");
            secondOperand = in.nextInt();
            mathResult = operand + secondOperand;
            listResult.add(mathResult);
        }
        if (mathAction.equals("*")) {
            System.out.println("Input second operand");
            secondOperand = in.nextInt();
            mathResult = operand * secondOperand;
            listResult.add(mathResult);
        }
        if (mathAction.equals("-")) {
            System.out.println("Input second operand");
            secondOperand = in.nextInt();
            mathResult = operand - secondOperand;
            listResult.add(mathResult);
        }
        if (mathAction.equals("/")) {
            System.out.println("Input second operand");
            secondOperand = in.nextInt();
            mathResult = operand / secondOperand;
            listResult.add(mathResult);
        }
        if (mathAction.equals("=")) {
            printResult(listResult);
            System.out.println("List of answers: " + listResult);
        } else {
            startApp(listResult);
        }
    }

    @Override
    public void foo() {

    }
}