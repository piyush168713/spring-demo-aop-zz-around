package com.luv2code.aopdemo;

public class Account {

    private String name;
    private String level;

    // default constructor
    public Account(){

    }

    // arg constructor
    public Account(String name, String level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }

    // add toString() method
    @Override
    public String toString() {
        return "Account: [" +
                "name='" + name + '\'' +
                ", level='" + level + '\'' +
                ']';
    }
}
