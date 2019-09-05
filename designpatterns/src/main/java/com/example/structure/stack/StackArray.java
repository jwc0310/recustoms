package com.example.structure.stack;


/**
 * 特性： 后进先出
 */
public class StackArray {


    //使用数组定义一个堆栈
    private class Stack {
        private static final int size = 10;
        Object[] objs = new Object[size];
        int index = 0; //下标

        //入栈
        public boolean push(Object obj) {
            if (index == size -1)
                return false;

            objs[++index] = obj;
            return true;
        }
        //出栈
        public Object pop() {
            if (index < 0)
                return null;
            return objs[index--];
        }

    }





}
