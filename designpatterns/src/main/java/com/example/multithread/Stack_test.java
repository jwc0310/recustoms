package com.example.multithread;

public class Stack_test {

    public static void main(String[] args) {

        Test2 test2 = new Test2();
        test2.a();

        Test1 test1 = new Test1();
        test1.a();
    }
}

class Test2 {

    public void a() {
        b();
    }

    private void b() {
        c();
    }

    private void c() {
        d();
    }

    private void d() {
        e();
    }

    public void e() {
        int age = 0;
        age = 100;
        if (100 == age) {
            Thread.dumpStack();
        }
    }
}



class Test1 {
    public void a() {
        b();
    }

    private void b() {
        c();
    }

    private void c() {
        d();
    }

    private void d() {
        e();
    }

    public void e() {
        StackTraceElement[] array = Thread.currentThread().getStackTrace();
        if (array != null) {
            int length = array.length;
            for (int i = 0; i < length; i++) {
                StackTraceElement element = array[i];
                System.out.println("classname: " + element.getClassName() + ", methodName: " + element.getMethodName() + ", fileName: "
                    + element.getFileName() + ", lineNo: " + element.getLineNumber());
            }
        }
    }
}
