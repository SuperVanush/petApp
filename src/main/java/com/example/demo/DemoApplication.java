package com.example.demo;

import com.example.demo.calc.NatalyaList;

public class DemoApplication {

    public static void main(String[] args) {
//
//        Integer[] mas = {12, 23, 45, 6, 3, 2, 34, 3, 3, 3, 23};
//
//        List<Integer> mas1 = List.of(mas);
//        long z = System.nanoTime();
//        Map<Integer, Integer> collect = mas1.stream().collect(Collectors.toMap(Function.identity(), i -> 1, Integer::sum));
//        System.out.println(System.nanoTime() - z);
//        long x = System.nanoTime();
//
//        List<Map.Entry<Integer, Long>> entryList = mas1.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().filter(i -> i.getValue() > 1).collect(Collectors.toList());
//        System.out.println(System.nanoTime() - x);
//
//        Map<Integer, Integer> integerMap = new HashMap<>();
//        int count = 1;
//        long y = System.nanoTime();
//        for (int i = 0; i < mas.length; i++) {
//            for (int j = i + 1; j < mas.length; j++) {
//                if (mas[i] == mas[j]) {
//                    if (integerMap.containsKey(mas[i]) && integerMap.get(mas[i]) < count) {
//                        integerMap.put(mas[i], ++count);
//                    } else integerMap.putIfAbsent(mas[i], ++count);
//                }
//                if (j == mas.length - 1) {
//                    count = 0;
//                }
//            }
//        }
//        System.out.println(System.nanoTime() - y);
        NatalyaList natalyaList = new NatalyaList();
        natalyaList.StartApp();
    }
}



