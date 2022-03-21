package NewCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StartCalculator {
    public static final Scanner in = new Scanner(System.in);
    private final List<Integer> listResult = new ArrayList<Integer>();

    int nextResult;


    public void StartCalculator() {
        System.out.println("Calculator");
        System.out.println("Q = Exit");
        FirstAction();
    }

    public void FirstAction() {
        int firstOperand;
        String action;
        int secondOperand;
        int firstResult = 0;
        firstOperand = in.nextInt();
        action = in.next();
        secondOperand = in.nextInt();
        if (action.equals("+")) {
            firstResult = firstOperand + secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            NextAction(firstResult, secondOperand);
        }
        if (action.equals("-")) {
            firstResult = firstOperand - secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            NextAction(firstResult, secondOperand);
        }
        if (action.equals("*")) {
            firstResult = firstOperand * secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            NextAction(firstResult, secondOperand);
        }
        if (action.equals("/")) {
            firstResult = firstOperand / secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            NextAction(firstResult, secondOperand);
        }
        if (action.equals("Q")) {
            System.out.println("Exit");
            System.out.println("Result=" + firstResult);

        }
    }

      public void NextAction(int nextResult, int nextOperand) {
        String nextAction;
               do {
               nextAction= in.next();
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
        while (!nextAction.equals("Q"))||(nextOperand!=);
        System.out.println("Exit");
        System.out.println(("Result =" + nextResult));
    }

}