package com.andy.frameworks.java;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;

public class Annota {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.CONSTRUCTOR)
    public @interface UserData {
        public int id() default 0;
        public String name() default "";
        public int age() default 0;
    }

    public static class Data {

        private int id;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        private int age;
        private String name;

        @UserData(id=1,name="xxx",age=10)
        public Data() {

        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", age='" + age + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Data(int id, int age, String name) {
            this.id = id;
            this.age = age;
            this.name = name;
        }
    }


    //运行时注解
    public static class AnnotationProcess {
        public void ini(Object object) {
            if (! (object instanceof Data)) {
                throw new IllegalArgumentException("[" + object.getClass().getSimpleName() +"]");
            }

            Constructor[] constructors = object.getClass().getDeclaredConstructors();
            for (Constructor constructor : constructors) {
                if (constructor.isAnnotationPresent(UserData.class)) {
                    UserData userData = (UserData) constructor.getAnnotation(UserData.class);
                    int age = userData.age();
                    int id = userData.id();
                    String name = userData.name();
                    ((Data)object).setAge(age);
                    ((Data)object).setId(id);
                    ((Data)object).setName(name);
                }
            }
        }
    }

//
//    public static class MyAnnotationProcess extends AbstractProcessor {
//
//    }

    public static void main(String[] args) {
        Data data = new Data();
        new AnnotationProcess().ini(data);
        System.out.print(data.toString());
    }

}
