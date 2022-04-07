package com.example.newcalculator.service;

import com.example.newcalculator.dao.CalculatorStorage;
import com.example.newcalculator.model.IntResult;

import java.util.List;

public class ServiceCalculator {
    private CalculatorStorage calculatorStorage = new CalculatorStorage();

    public int mathCalculation(int firstOperand, int secondOperand, String action) {
        IntResult intResult = new IntResult();
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
        intResult.setValue(result);
        calculatorStorage.add(intResult);
        int resultReturn = calculatorStorage.findById(idLastReturn).getValue();
        return resultReturn;
    }

    public int mathCalculation(String nextAction, int nextOperand) {
        int result = 0;
        List<IntResult> listForLastIndex = calculatorStorage.getListOfElements();
        int indexLastElement = listForLastIndex.size() - 1;
        int lastResultReturn = listForLastIndex.get(indexLastElement).getValue();
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
        int idLastReturn = calculatorStorage.add(intResult);
        lastResultReturn = calculatorStorage.findById(idLastReturn);
        return lastResultReturn;
    }

    public List<IntResult> resultForPrint() {
        return calculatorStorage.getListOfElements();
    }
}