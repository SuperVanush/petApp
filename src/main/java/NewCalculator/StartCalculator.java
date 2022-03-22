package NewCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StartCalculator {
    Servise servise = new Servise();

    public static final Scanner in = new Scanner(System.in);


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
        System.out.println("Input number");
        firstOperand = in.nextInt();
        System.out.println("Input action");
        action = in.next();
        System.out.println("Input next number");
        secondOperand = in.nextInt();
        servise.firstMathCalculation(int firstOperand,int secondOperand, String action);
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

            }
        }
        while (!nextAction.equals("q"));
    }
}
