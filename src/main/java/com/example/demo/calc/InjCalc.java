package com.example.demo.calc;

public class InjCalc implements Calculate {

    public void startApp() {
        System.out.println("First");
        int[] ints = {2,9,7,8,9,6,5,1,3,5,13,5,6};
        System.out.println("Done");
//        integerList.add(operandOne);
//
//        System.out.println("Operat");
//        String operation = in.next();
//        operationList.add(operation);
//
//        System.out.println("Second");
//        int operandTwo = in.nextInt();
//        integerList.add(operandTwo);
//
//        System.out.println("Terminate?");
//        String terminate = in.next();
//        int firstMathResult = 0;
//
//        if (terminate.equals("=")) {
//            for (int i = 0; i < integerList.size() - 1; i++) {
//                for (String s : operationList) {
//                    if (s.equals("+")) {
//                        firstMathResult = integerList.get(i) + integerList.get(i + 1);
//                        System.out.println(firstMathResult);
//                        startApp();
//                    }
//                    if (operation.equals("-")) {
//                        firstMathResult = integerList.get(i) - integerList.get(i + 1);
//                        System.out.println(firstMathResult);
//                        startApp();
//                    }
//                    if (operation.equals("*")) {
//                        firstMathResult = integerList.get(i) * integerList.get(i + 1);
//                        System.out.println(firstMathResult);
//                        startApp();
//                    }
//                    if (operation.equals("/")) {
//                        firstMathResult = integerList.get(i) / integerList.get(i + 1);
//                        System.out.println(firstMathResult);
//                        startApp();
//                    }
//                }
            }

    @Override
    public void foo() {
        System.out.println("Injection");
    }
//        } else startApp();
//    }
}
