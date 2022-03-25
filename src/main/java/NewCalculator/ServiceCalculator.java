package NewCalculator;

import java.util.ArrayList;
import java.util.List;

public class ServiceCalculator {
    private final List<Integer> listResult = new ArrayList<>();


    public int mathCalculation(int firstOperand, int secondOperand, String action) {
        int firstResult;
        if (action.equals("+")) {
            firstResult = firstOperand + secondOperand;
            listResult.add(firstResult);
        }
        if (action.equals("-")) {
            firstResult = firstOperand - secondOperand;
            listResult.add(firstResult);
        }
        if (action.equals("*")) {
            firstResult = firstOperand * secondOperand;
            listResult.add(firstResult);
        }
        if (action.equals("/")) {
            firstResult = firstOperand / secondOperand;
            listResult.add(firstResult);
        }
        return listResult.get(listResult.size() - 1);
    }

    public int mathCalculation(String nextAction, int nextOperand) {
        int nextResult;
        if (nextAction.equals("+")) {
            nextResult = listResult.get(listResult.size() - 1) + nextOperand;
            listResult.add(nextResult);
        }
        if (nextAction.equals("-")) {
            nextResult = listResult.get(listResult.size() - 1) - nextOperand;
            listResult.add(nextResult);
        }
        if (nextAction.equals("*")) {
            nextResult = listResult.get(listResult.size() - 1) * nextOperand;
            listResult.add(nextResult);
        }
        if (nextAction.equals("/")) {
            nextResult = listResult.get(listResult.size() - 1) / nextOperand;
            listResult.add(nextResult);
        }
        return listResult.get(listResult.size() - 1);
    }

    @Override
    public String toString() {
        return "ServiceCalculator{" +
                "listResult=" + listResult +
                '}';
    }
}
