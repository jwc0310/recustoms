package com.andy.recustomviews.java.javatips;
import android.hardware.Camera.Size;

/**
 * Created by Administrator on 2017/10/16.
 */

public class tips {

    /**
     * equals 用来判断两个对象是否相等
     *
     */

    public static void main(String[] args) {
        Person person1 = new Person(11, "abc");
        Person person2 = new Person(12, "def");
    }

    private static class Person{
        private int age;
        private String name;

        private Person(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
