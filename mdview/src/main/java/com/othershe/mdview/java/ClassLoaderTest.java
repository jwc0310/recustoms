package com.othershe.mdview.java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClassLoaderTest {

    /**
     *  classloader 类型
     *  系统类加载
     *      Bootstrap ClassLoader、 Extensions ClassLoader 和 App ClassLoader
     *
     *  自定义类加载器
     *
     */


    /**
     *  类加载器查找class：双亲委托模式
     *
     *  双亲委托模式
     *      首先判断该Class是否已经加载，如果没有则不是自身去查找而是委托给父加载器进行查找，
     *      这样依次的进行递归，直到委托到最顶层的Bootstrap ClassLoader，如果Bootstrap ClassLoader找到了该Class，
     *      就会直接返回，如果没找到，则继续依次向下查找，
     *      如果还没找到则最后会交由自身去查找
     *
     */


    public static void main(String[] args) {
        // customClassloader();
        printClassLoaderFamilies();

    }


    //custom classloader

    /**
     *  custom classloader
     *
     *  定义一个自定义ClassLoader并继承抽象类ClassLoader。
     *  复写findClass方法，并在findClass方法中调用defineClass方法
     *
     */

//    private static void customClassloader() {
//
//
//        DiskClassLoader diskClassLoader = new DiskClassLoader("D:\\com\\example");//1
//        try {
//            Class c = diskClassLoader.loadClass("com.example.Jobs");//2
//            if (c != null) {
//                try {
//                    Object obj = c.newInstance();
//                    System.out.println(obj.getClass().getClassLoader());
//                    Method method = c.getDeclaredMethod("says", null);
//                    method.invoke(obj, null);//3
//                } catch (InstantiationException | IllegalAccessException
//                        | NoSuchMethodException
//                        | SecurityException |
//                        IllegalArgumentException |
//                        InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }

    private static void printClassLoaderFamilies() {
        ClassLoader loader = ClassLoaderTest.class.getClassLoader();
        while (loader != null) {
            System.out.println(loader);
            loader = loader.getParent();
        }
    }


    private static void printSystemClassLoader() {
        //Bootstrap ClassLoader
        String  bootstrap = System.getProperty("sun.boot.class.path");
        printSplit(bootstrap, ";");

        //Extensions ClassLoader
        String extensions = System.getProperty("java.ext.dirs");
        printSplit(extensions, ";");
    }


    private static void printSplit(String args, String split) {
        if (args == null)
            return;

        String[] splits = args.split(split);
        if (splits == null || splits.length == 0)
            return;

        System.out.println();
        for (String string : splits) {
            System.out.println(string);
        }
    }


}
