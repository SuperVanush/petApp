package NewCalculator;

import java.util.ArrayList;
import java.util.List;

public class ServiceCalculator {
    StartCalculator startCalculator = new StartCalculator();
    private final List<Integer> listResult = new ArrayList<>();


    public void firstMathCalculation(int firstOperand, int secondOperand, String action) {
        int firstResult;
        if (action.equals("+")) {
            firstResult = firstOperand + secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            startCalculator.nextAction();
        }
        if (action.equals("-")) {
            firstResult = firstOperand - secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            startCalculator.nextAction();
        }
        if (action.equals("*")) {
            firstResult = firstOperand * secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            startCalculator.nextAction();
        }
        if (action.equals("/")) {
            firstResult = firstOperand / secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            startCalculator.nextAction();
        }
    }

    public void nextMathCalculation(int nextResult, int nextOperand, String nextAction) {
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
