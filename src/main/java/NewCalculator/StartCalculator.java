package NewCalculator;

import java.util.Scanner;

public class StartCalculator {
    ServiceCalculator serviceCalculator = new ServiceCalculator();

    public static final Scanner in = new Scanner(System.in);


    public void startCalculator() {
        System.out.println("Calculator");
        System.out.println("Q = Exit");
        firstAction();
    }

    public void firstAction() {
        System.out.println("Input number");
        int firstOperand = in.nextInt();
        System.out.println("Input action");
        String action = in.next();
        System.out.println("Input next number");
        int secondOperand = in.nextInt();
        serviceCalculator.firstMathCalculation(firstOperand, secondOperand, action);
    }

    public void nextAction() {
        int nextResult = 0;
        String nextAction;
        do {
            System.out.println("Input action or 'q' for Exit");
            nextAction = in.next();
            if (nextAction.equals("q")) {
                System.out.println("Exit");
                System.out.println(("Result =" + nextResult));
            } else {
                System.out.println("Input next number");
                int nextOperand = in.nextInt();
                serviceCalculator.nextMathCalculation(nextResult, nextOperand, nextAction);

            }
        }
        while (!nextAction.equals("q"));
    }
}
