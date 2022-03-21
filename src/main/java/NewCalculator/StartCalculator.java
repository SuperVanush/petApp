package NewCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StartCalculator {
    public static final Scanner in = new Scanner(System.in);
    private final List<Integer> listResult = new ArrayList<Integer>();


    public void startCalculator() {
        System.out.println("Calculator");
        System.out.println("Q = Exit");
        firstAction();
    }

    public void firstAction() {
        int nextResult = 0;
        int firstOperand;
        String action;
        int secondOperand;
        int firstResult = 0;
        System.out.println("Input number");
        firstOperand = in.nextInt();
        System.out.println("Input action");
        action = in.next();
        System.out.println("Input next number");
        secondOperand = in.nextInt();
        if (action.equals("+")) {
            firstResult = firstOperand + secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            nextAction(nextResult);
        }
        if (action.equals("-")) {
            firstResult = firstOperand - secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            nextAction(nextResult);
        }
        if (action.equals("*")) {
            firstResult = firstOperand * secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            nextAction(nextResult);
        }
        if (action.equals("/")) {
            firstResult = firstOperand / secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            nextAction(nextResult);
        }
    }

    public void nextAction(int nextResult) {

        int nextOperand;
        String nextAction;
        do {
            System.out.println("Input action or 'q' for Exit");
            nextAction = in.next();
            if (nextAction.equals("q")) {
                System.out.println("Exit");
                System.out.println(("Result =" + nextResult));
            } else {
                System.out.println("Input next number");
                nextOperand = in.nextInt();
                if (nextAction.equals("+")) {
                    nextResult = listResult.get(listResult.size() - 1) + nextOperand;
                    listResult.add(nextResult);
                    System.out.println(nextResult);
                }
                if (nextAction.equals("-")) {
                    nextResult = listResult.get(listResult.size() - 1) - nextOperand;
                    listResult.add(nextResult);
                    System.out.println(nextResult);
                }
                if (nextAction.equals("*")) {
                    nextResult = listResult.get(listResult.size() - 1) * nextOperand;
                    listResult.add(nextResult);
                    System.out.println(nextResult);
                }
                if (nextAction.equals("/")) {
                    nextResult = listResult.get(listResult.size() - 1) / nextOperand;
                    listResult.add(nextResult);
                    System.out.println(nextResult);
                }
            }
        }
        while (!nextAction.equals("q"));
    }
}