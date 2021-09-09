package com.example.demo.sort;


public class User implements Comparable <User> {
       private String name;
         private int number;
public User(String name, int number){
    this.name= name;
    this.number = number;
}

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int compareTo(User o) {

        if (number== o.getNumber())
            return 0;
        else {if (number > o.getNumber())
        return 1;
        else return -1;}
    }

}


