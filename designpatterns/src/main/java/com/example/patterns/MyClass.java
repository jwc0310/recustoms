package com.example.patterns;

import com.example.patterns.template.Abstract;
import com.example.patterns.template.Concrete;

public class MyClass {

    public static void main(String[] args) {
        Abstract aa = new Concrete();
        aa.print("abc");
    }

}
