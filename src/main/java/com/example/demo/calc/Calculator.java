package com.example.demo.calc;

import java.util.List;
import java.util.Scanner;

public class Calculator {

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

    private void printResult(List<Integer> listResult) {
        int result = 0;
        for (Integer integer : listResult) {
            result = integer + result;
        }
        System.out.println("Common result: " + result);
    }

    private void startTwo(List<Integer> listResult, int firstOperand) {
        String firstMathAction;
        do {
            System.out.println("Input math action");
            firstMathAction = in.next();

            if (firstMathAction.equals("+")) {
                actionSumm(listResult, firstOperand);
            }
            if (firstMathAction.equals("*")) {
                actionMultiplication(listResult, firstOperand);
            }
            if (firstMathAction.equals("-")) {
                actionDifferense(listResult, firstOperand);
            }
            if (firstMathAction.equals("/")) {
                actionDivision(listResult, firstOperand);
            }
            if (firstMathAction.equals("=")) {
                actionEquaiiy(listResult);
            }
        }
        while (firstMathAction.equals("+") || firstMathAction.equals("-") || firstMathAction.equals("*") || firstMathAction.equals("/"));
    }

    private void actionSumm(List<Integer> listResult, int firstOperand) {
        System.out.println("Input second operand");
        int secondOperand = in.nextInt();
        listResult.add(firstOperand + secondOperand);
    }

    private void actionMultiplication(List<Integer> listResult, int firstOperand) {
        System.out.println("Input second operand");
        int secondOperand = in.nextInt();
        listResult.add(firstOperand * secondOperand);
    }

    private void actionDifferense(List<Integer> listResult, int firstOperand) {
        System.out.println("Input second operand");
        int secondOperand = in.nextInt();
        listResult.add(firstOperand - secondOperand);
    }

    private void actionDivision(List<Integer> listResult, int firstOperand) {
        System.out.println("Input second operand");
        int secondOperand = in.nextInt();
        listResult.add(firstOperand / secondOperand);
    }

    private void actionEquaiiy(List<Integer> listResult) {
        printResult(listResult);
        System.out.print("List of answers: " + listResult);
    }
}