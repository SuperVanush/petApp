package NewCalculator;

import java.util.Scanner;

public class StartCalculator {
    ServiceCalculator serviceCalculator = new ServiceCalculator();

    public static final Scanner in = new Scanner(System.in);

    public void startCalculator() {
        System.out.println("Calculator");
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
        System.out.println(serviceCalculator.firstMathCalculation(firstOperand, secondOperand, action));
        nextAction();
    }

    public void nextAction() {
        int nextOperand = 0;
        String nextAction;
        do {
            System.out.println("Input action or 'q' for Exit");
            nextAction = in.next();
            if (nextAction.equals("q")){
                System.out.println(("Result =" + serviceCalculator.nextMathCalculation(nextAction, nextOperand)));
                System.exit(0);
            }
            System.out.println("Input next number");
            nextOperand = in.nextInt();
            int actionResult = serviceCalculator.nextMathCalculation(nextAction, nextOperand);
            System.out.println(("Result =" + actionResult));
        }
        while (!nextAction.equals("q"));
        System.out.println("Exit");

    }
}
