package com.andy.recustomviews.bean;

public class Girl {
    public String country;
    public String city;
    private String name;
    private int age;
    private int bust;
    private int waist;
    private int hip;

    public Girl(){
        System.out.println("调用Girl的无参构造方法");
    }

    public Girl(String name, int waist, int bust, int hip, int age) {
        this.name = name;
        this.waist = waist;
        this.bust = bust;
        this.age = age;
        this.hip = hip;
    }

    private Girl(String name,Integer age){
        this.name = name;
        this.age=age;
    }

    private String getMobile(String number){
        String mobile="010-110"+"-"+number;
        return  mobile;
    }

    @Override
    public String toString() {
        return "Girl{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", bust=" + bust +
                ", waist=" + waist +
                ", hip=" + hip +
                '}';
    }
}