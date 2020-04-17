package com.example.structure.stack;


/**
 * ���ԣ� ����ȳ�
 */
public class StackArray {


    //ʹ�����鶨��һ����ջ
    private class Stack {
        private static final int size = 10;
        Object[] objs = new Object[size];
        int index = 0; //�±�

        //��ջ
        public boolean push(Object obj) {
            if (index == size -1)
                return false;

            objs[++index] = obj;
            return true;
        }
        //��ջ
        public Object pop() {
            if (index < 0)
                return null;
            return objs[index--];
        }

    }





}
