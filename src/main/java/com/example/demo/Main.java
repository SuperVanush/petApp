package com.example.demo;

import com.example.demo.view.StartProgram;
import com.example.demo.view.ViewConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ViewConfig.class);
        StartProgram startProgram = context.getBean(StartProgram.class);
        startProgram.startApp();
    }
}