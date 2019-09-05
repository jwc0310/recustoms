package com.othershe.mdview.net.rxjava;

import java.util.ArrayList;

public class Book {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getHistroy() {
        return histroy;
    }

    public void setHistroy(ArrayList<String> histroy) {
        this.histroy = histroy;
    }

    private String name;
    private ArrayList<String> histroy; //修订记录

    public Book(String name, ArrayList<String> histroy) {
        this.name = name;
        this.histroy = histroy;
    }
}
