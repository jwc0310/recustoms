package com.example.lambda;

public class Lambda_test {

    public static void main(String[] args) {
        Lambda_test test = new Lambda_test();

        // 声明类型
        MathOperation addition = (int a, int b) -> a + b;
        // 不声明类型
        MathOperation subtraction = (a, b) -> a - b;

        //

        GreetingService greetingService = (String s) -> {
            System.out.println(s);
        };

        int res = test.operate(5, 6, addition);
        greetingService.sayMessage(res+"");

        res = test.operate(5, 6, subtraction);
        greetingService.sayMessage(res+"");


    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }

}
