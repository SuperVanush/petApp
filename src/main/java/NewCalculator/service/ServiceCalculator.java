package NewCalculator.service;

import NewCalculator.dao.CalculatorStorage;

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
        List<Integer> listForLastIndex = calculatorStorage.getListOfElements();
        int indexLastElement = listForLastIndex.size() - 1;
        int lastResultReturn = listForLastIndex.get(indexLastElement);
        if (nextAction.equals("+")) {
            result = lastResultReturn + nextOperand;
        }
        if (nextAction.equals("-")) {
            result = lastResultReturn - nextOperand;
        }
        if (nextAction.equals("*")) {
            result = lastResultReturn * nextOperand;
        }
        if (nextAction.equals("/")) {
            result = lastResultReturn / nextOperand;
        }
        int idLastReturn = calculatorStorage.add(result);
        lastResultReturn = calculatorStorage.findById(idLastReturn);
        return lastResultReturn;
    }

    public List<Integer> resultForPrint() {
        return calculatorStorage.getListOfElements();
    }
}