package NewCalculator;

import java.util.List;

public class ServiceCalculator {
    private CalculatorStorage calculatorStorage = new CalculatorStorage();

    public int mathCalculation(int firstOperand, int secondOperand, String action) {
        int result = 0;
        int idLastReturn = 0;
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
        calculatorStorage.add(result);
        int resultReturn = calculatorStorage.findById(idLastReturn);
        return resultReturn;
    }

    public int mathCalculation(String nextAction, int nextOperand) {
        int result = 0;
        int resultReturn = calculatorStorage.getListOfElements().get((calculatorStorage.getListOfElements().size() - 1));
        if (nextAction.equals("+")) {
            result = resultReturn + nextOperand;
        }
        if (nextAction.equals("-")) {
            result = resultReturn - nextOperand;
        }
        if (nextAction.equals("*")) {
            result = resultReturn * nextOperand;
        }
        if (nextAction.equals("/")) {
            result = resultReturn / nextOperand;
        }
        int idLastReturn = calculatorStorage.add(result);
        resultReturn = calculatorStorage.findById(idLastReturn);
        return resultReturn;
    }

    public List<Integer> resultForPrint() {
        return calculatorStorage.getListOfElements();
    }
}