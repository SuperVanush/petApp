package com.example.demo;

import com.example.demo.view.StartProgram;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Main {

    public static void main(String[] args) {
        StartProgram startProgram = new StartProgram();
        Logger.getRootLogger().setLevel(Level.OFF);
        startProgram.startApp();
    }
}