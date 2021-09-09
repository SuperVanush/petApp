package com.example.demo.calc;

import java.util.ArrayList;
import java.util.List;

public class Starter {

    public void start() {
        List<Calculate> calculates = new ArrayList<>();
        calculates.add(new Converter());
        calculates.add(new Foo());
        calculates.add(new InjCalc());
        calculates.add(new Finish());

        for (Calculate calculate : calculates) {
            calculate.foo();
        }
    }
}
