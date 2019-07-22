package com.andy.materialtest.bases;

public class TestEvent {

    private String msg;


    public TestEvent(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "TestEvent{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
