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
     * ���󣺲�Ʒ
     * �������� ����
     *
     * ��������ģʽ�� ���޸�ԭ�����������������µĲ�Ʒ�� �����㿪��ԭ��
     *
     * 1�� �û�ֻ��Ҫ֪�����幤�������ƾͿ��Եõ���Ҫ�Ĳ�Ʒ�� ����֪����Ʒ������ṩ��
     * 2�� �����²�Ʒ��ֻ��Ҫ��Ӿ����Ƭ��Ͷ�Ӧ�Ĺ����࣬�����ԭ���������κ��޸ģ� ���㿪��ԭ��
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


//�����Ʒ�� �ṩ��Ʒ�ӿ�
interface Product {
    public void show();
}

//�����Ʒ1�� ʵ�ֲ�Ʒ�ĳ��󷽷�
class ConcreteProduct1 implements Product {

    @Override
    public void show() {
        System.out.println("�����Ʒ1��ʾ......");
    }
}

//�����Ʒ2
class ConcreteProduct2 implements Product {

    @Override
    public void show() {
        System.out.println("�����Ʒ2��ʾ......");
    }
}


//���󹤳��� �ṩ��Ʒ�����ɷ���
//����: ���ɲ�Ʒ
//��Ʒ�� ���Ƕ���
interface AbstractFactory {
    public Product newProduct();
}

//���幤��2��  ʵ���˲�Ʒ�����ɷ���
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
    //��xml�����ļ��л�ȡ���������� ������ʵ������
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


