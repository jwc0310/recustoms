package com.example.structure.trees;

import java.util.LinkedList;
import java.util.List;

public class BinaryTree {

    public static void main(String[] args) {

        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.createBinaryTree(array);

        BinaryNode root = nodeList.get(0);

        System.out.println("pre:");
        binaryTree.preOrderTraverse(root);
        System.out.println("\nin:");
        binaryTree.inOrderTraverse(root);
        System.out.println("\npos:");
        binaryTree.postOrderTraverse(root);

    }

    private static List<BinaryNode> nodeList = null;


    public void createBinaryTree(int[] array) {
        if (nodeList == null) {
            nodeList = new LinkedList<>();
        }

        nodeList.clear();
        //��һ������һ��ת��ΪNode�ڵ�
        for (int nodeIndex = 0; nodeIndex < array.length; nodeIndex ++) {
            nodeList.add(new BinaryNode(array[nodeIndex]));
        }

        //��ǰlastParentIndex-1�����ڵ㰴�ո��ڵ��뺢�ӽڵ�����ֹ�ϵ����������
        for (int parentIndex = 0; parentIndex < array.length / 2 -1; parentIndex++) {
            //����
            nodeList.get(parentIndex).leftChild = nodeList.get(parentIndex * 2 + 1);
            //�Һ���
            nodeList.get(parentIndex).rightChild = nodeList.get(parentIndex * 2 + 2);
        }

        //���һ�����ڵ㣺��Ϊ���һ�����ڵ����û���Һ��ӣ����Ե����ó�������
        int lastParentIndex = array.length / 2 - 1;
        //����
        nodeList.get(lastParentIndex).leftChild = nodeList.get(lastParentIndex * 2 + 1);

        //�Һ���  ������鳤��Ϊ�����Ž����Һ���
        if (array.length % 2 == 1) {
            nodeList.get(lastParentIndex).rightChild = nodeList.get(lastParentIndex * 2 + 2);
        }

    }

    /** ����ͷ�����ʵ��Ⱥ�˳�� **/
    /**
     * �������
     * ������
     */
    public void preOrderTraverse(BinaryNode node) {
        if (node == null)
            return;

        System.out.print(node.data +" ");
        preOrderTraverse(node.leftChild);
        preOrderTraverse(node.rightChild);
    }

    /**
     * �������
     * ������
     */
    public void inOrderTraverse(BinaryNode node) {
        if (node == null)
            return;

        inOrderTraverse(node.leftChild);
        System.out.print(node.data +" ");
        inOrderTraverse(node.rightChild);
    }


    /**
     * ��������
     * ������
     */
    public void postOrderTraverse(BinaryNode node) {
        if (node == null)
            return;
        postOrderTraverse(node.leftChild);
        postOrderTraverse(node.rightChild);
        System.out.print(node.data +" ");
    }



}
