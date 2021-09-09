package com.example.demo.calc;


public class Foo implements Calculate{

    public void someMethod(){
        System.out.println("Some");
    }


    @Override
    public void foo() {
        System.out.println("FOO");
    }
}
