package com.example.structure.trees;

public class BinaryNode implements Comparable<BinaryNode> {
    BinaryNode leftChild;
    BinaryNode rightChild;
    int data;


    public BinaryNode(int data) {
        this.leftChild = null;
        this.rightChild = null;
        this.data = data;
    }


    @Override
    public int compareTo(BinaryNode o) {
        return data - o.data;
    }
}