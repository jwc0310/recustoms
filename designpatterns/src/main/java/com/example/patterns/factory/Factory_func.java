package com.example.patterns.factory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Factory_func {

    /**
     *
     * 对象：产品
     * 创建对象： 工厂
     *
     * 工厂方法模式： 不修改原来代码的情况下引进新的产品， 即满足开闭原则
     *
     * 1， 用户只需要知道具体工厂的名称就可以得到所要的产品， 无需知道产品具体的提供者
     * 2， 增加新产品，只需要添加具体产片类和对应的工厂类，无需对原工厂进行任何修改， 满足开闭原则
     *
     */

    public static void main(String[] args) {

        Product product;
        AbstractFactory af;
        af = (AbstractFactory) ReadXML1.getObject();
        product = af.newProduct();
        product.show();
    }

}


//抽象产品： 提供产品接口
interface Product {
    public void show();
}

//具体产品1： 实现产品的抽象方法
class ConcreteProduct1 implements Product {

    @Override
    public void show() {
        System.out.println("具体产品1显示......");
    }
}

//具体产品2
class ConcreteProduct2 implements Product {

    @Override
    public void show() {
        System.out.println("具体产品2显示......");
    }
}


//抽象工厂： 提供产品的生成方法
//工厂: 生成产品
//产品： 就是对象
interface AbstractFactory {
    public Product newProduct();
}

//具体工厂2：  实现了产品的生成方法
class ConcreteFactory1 implements AbstractFactory {

    @Override
    public Product newProduct() {
        System.out.println("concrete factory 1 product ---> concrete product 1.....");
        return new ConcreteProduct1();
    }
}

class ConcreteFactory2 implements AbstractFactory {

    @Override
    public Product newProduct() {
        System.out.println("concrete factory 2 product ---> concrete product 2.....");
        return new ConcreteProduct2();
    }
}

class ReadXML1 {
    //从xml配置文件中获取具体类名， 并返回实例对象
    public static Object getObject() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document doc = builder.parse(new File(".\\designpatterns\\src\\main\\java\\com\\example\\patterns\\factory\\config1.xml"));
            NodeList nl = doc.getElementsByTagName("className");
            Node classNode = nl.item(0).getFirstChild();

            String cName = "com.example.patterns.factory." + classNode.getNodeValue();
            System.out.println("new class name: " + cName);

            Class<?> c = Class.forName(cName);
            Object object = c.newInstance();
            return object;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


