package com.andy.recustomviews.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */
public class IteratorTest {

    public static void main(String[] args){
        Integer[] strings = new Integer[] {1,2,3,4,5,6,7,8,9};
        List<Integer> list = Arrays.asList(strings);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()){
            int i = iterator.next();
            println(i);
        }

        List<Integer> list1 = new ArrayList<>();
    }

    private static void println(int i){
        System.out.println("== "+i+" ==");
    }

}
