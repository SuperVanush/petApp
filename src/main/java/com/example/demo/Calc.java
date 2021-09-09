package com.example.demo;

import java.util.Scanner;

public class Calc {

    private int result;

    public void getCalc(int someResult) {
        Scanner in = new Scanner(System.in);
        System.out.println("Print first");
        int firstOperand = in.nextInt();
        System.out.println("Print operation");
        String firstMathAction = in.next();
        System.out.println("Print Second");
        int secondOperand = in.nextInt();
        System.out.println("Print");
        String is = in.next();
        int firstMathResult;

        switch (firstMathAction) {
            case "+":
                firstMathResult = firstOperand + secondOperand;
                if (someResult != 0){
                    result = someResult + firstMathResult;
                }
                if (is.equals("=")){
                    System.out.print(result);
                    System.out.println(someResult);
                }
                getCalc(firstMathResult);
                break;
            case "-":
                firstMathResult = firstOperand - secondOperand;
                if (someResult != 0){
                    result = someResult + firstMathResult;
                }
                getCalc(firstMathResult);
                break;
            case "*":
                firstMathResult = firstOperand * secondOperand;
                if (someResult != 0){
                    result = someResult + firstMathResult;
                }
                getCalc(firstMathResult);
                break;
            case "/":
                firstMathResult = firstOperand / secondOperand;
                if (someResult != 0){
                    result = someResult + firstMathResult;
                }
                getCalc(firstMathResult);
                break;
            default:
                System.out.print("ERROR");
                break;
        }
    }
}
