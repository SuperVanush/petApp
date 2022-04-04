package NewCalculator;

import com.example.demo.exception.UserListException;

import java.util.List;

public class ServiceCalculator {
    private CalculatorStorage calculatorStorage = new CalculatorStorage();
    int result = 0;
    int idLastReturn = 0;
    int resultReturn = 0;

    public int mathCalculation(int firstOperand, int secondOperand, String action) {
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
        try {
            resultReturn = calculatorStorage.findById(idLastReturn);
        } catch (UserListException e) {
            System.err.println(e.getMessage());
        }
        return resultReturn;
    }

    public int mathCalculation(String nextAction, int nextOperand) {
               try {
            resultReturn = calculatorStorage.findById(idLastReturn);
        } catch (UserListException e) {
            System.err.println(e.getMessage());
        }
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
        idLastReturn = calculatorStorage.add(result);
        try {
            resultReturn = calculatorStorage.findById(idLastReturn);
        } catch (UserListException e) {
            System.err.println(e.getMessage());
        }
        return resultReturn;
    }

    public List<Integer> resultForPrint() {
        return calculatorStorage.getListOfElements();
    }
}
