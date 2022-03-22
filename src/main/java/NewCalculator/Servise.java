package NewCalculator;

import java.util.ArrayList;
import java.util.List;

public class Servise {
    private final List<Integer> listResult = new ArrayList<Integer>();
    StartCalculator startCalculator = new StartCalculator();

    public void firstMathCalculation(int firstOperand,int secondOperand, String action) {
        int firstResult = 0;
        if (action.equals("+")) {
            firstResult = firstOperand + secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            startCalculator.nextAction(int nextResult);
        }
        if (action.equals("-")) {
            firstResult = firstOperand - secondOperand;
            listResult.add(firstResult);
            System.out.println(firstResult);
            startCalculator
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
    public void nextMathCalculation (int nextResult, int nextOperand ){
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
