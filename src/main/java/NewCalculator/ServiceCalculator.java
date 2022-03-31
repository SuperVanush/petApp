package NewCalculator;

import java.util.List;

public class ServiceCalculator {
    private CalculatorStorage calculatorStorage = new CalculatorStorage();

    public int mathCalculation(int firstOperand, int secondOperand, String action) {
        int result = 0;
        if (action.equals("+")) {
            result = firstOperand + secondOperand;
        }
        if (action.equals("-")) {
            result = firstOperand - secondOperand;
        }
        if (action.equals("*")) {
            result = firstOperand * secondOperand;
        }
        if (action.equals("/")) {
            result = firstOperand / secondOperand;
        }
        int resultReturn = calculatorStorage.add(result);
        return resultReturn;
    }

    public int mathCalculation(String nextAction, int nextOperand) {
        int result = 0;
        if (nextAction.equals("+")) {
            result = calculatorStorage.returnLatsResult() + nextOperand;
        }
        if (nextAction.equals("-")) {
            result = calculatorStorage.returnLatsResult() - nextOperand;
        }
        if (nextAction.equals("*")) {
            result = calculatorStorage.returnLatsResult() * nextOperand;
        }
        if (nextAction.equals("/")) {
            result = calculatorStorage.returnLatsResult() / nextOperand;
        }
        int resultReturn = calculatorStorage.add(result);
        return resultReturn;
    }

    public List<Integer> resultForPrint() {
        return calculatorStorage.getListOfElements();
    }
}
