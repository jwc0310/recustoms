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
        //将一个数组一次转换为Node节点
        for (int nodeIndex = 0; nodeIndex < array.length; nodeIndex ++) {
            nodeList.add(new BinaryNode(array[nodeIndex]));
        }

        //对前lastParentIndex-1个父节点按照父节点与孩子节点的数字关系建立二叉树
        for (int parentIndex = 0; parentIndex < array.length / 2 -1; parentIndex++) {
            //左孩子
            nodeList.get(parentIndex).leftChild = nodeList.get(parentIndex * 2 + 1);
            //右孩子
            nodeList.get(parentIndex).rightChild = nodeList.get(parentIndex * 2 + 2);
        }

        //最后一个父节点：因为最后一个父节点可能没有右孩子，所以单独拿出来处理
        int lastParentIndex = array.length / 2 - 1;
        //左孩子
        nodeList.get(lastParentIndex).leftChild = nodeList.get(lastParentIndex * 2 + 1);

        //右孩子  如果数组长度为奇数才建立右孩子
        if (array.length % 2 == 1) {
            nodeList.get(lastParentIndex).rightChild = nodeList.get(lastParentIndex * 2 + 2);
        }

    }

    /** 根据头结点访问的先后顺序 **/
    /**
     * 先序遍历
     * 中左右
     */
    public void preOrderTraverse(BinaryNode node) {
        if (node == null)
            return;

        System.out.print(node.data +" ");
        preOrderTraverse(node.leftChild);
        preOrderTraverse(node.rightChild);
    }

    /**
     * 中序遍历
     * 左中右
     */
    public void inOrderTraverse(BinaryNode node) {
        if (node == null)
            return;

        inOrderTraverse(node.leftChild);
        System.out.print(node.data +" ");
        inOrderTraverse(node.rightChild);
    }


    /**
     * 后续遍历
     * 左右中
     */
    public void postOrderTraverse(BinaryNode node) {
        if (node == null)
            return;
        postOrderTraverse(node.leftChild);
        postOrderTraverse(node.rightChild);
        System.out.print(node.data +" ");
    }



}
