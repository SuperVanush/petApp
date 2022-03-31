package NewCalculator;
import java.util.List;

public class ServiceCalculator {
    CalculatorStorage calculatorStorage = new CalculatorStorage();

    public int mathCalculation(int firstOperand, int secondOperand, String action) {
        int result=0;
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
        return result;
    }

    public int mathCalculation(String nextAction, int nextOperand) {
        int result=0;
        if (nextAction.equals("+")) {
            result = calculatorStorage.add(result) + nextOperand;
                              }
        if (nextAction.equals("-")) {
            result =  calculatorStorage.add(result) - nextOperand;
            }
        if (nextAction.equals("*")) {
            result =  calculatorStorage.add(result)  * nextOperand;
                   }
        if (nextAction.equals("/")) {
            result =  calculatorStorage.add(result) / nextOperand;
                 }
        calculatorStorage.add(result);
        return result;
    }

    public List<Integer> resultForPrint() {
        return calculatorStorage.getListOfElements();
    }
}
