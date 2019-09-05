package com.othershe.mdview.net.rxjava;

import java.util.ArrayList;

public class People {
    private String name;
    private ArrayList<Book> saveBooks;

    public People(String name, ArrayList<Book> saveBooks) {
        this.name = name;
        this.saveBooks = saveBooks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Book> getSaveBooks() {
        return saveBooks;
    }

    public void setSaveBooks(ArrayList<Book> saveBooks) {
        this.saveBooks = saveBooks;
    }
}
