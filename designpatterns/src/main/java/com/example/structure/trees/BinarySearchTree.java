package com.example.structure.trees;

/**
    ��������������ָһ�ÿ������߾����������ʵĶ�������

        ������ڵ�����������գ��������������нڵ��ֵ��С�����ĸ��ڵ��ֵ��
        ������ڵ�����������գ��������������нڵ��ֵ���������ĸ��ڵ��ֵ��
        ����ڵ����������Ҳ�ֱ�Ϊ������������
        û�м�ֵ��ȵĽڵ㡣
 **/
public class BinarySearchTree {

    private BinaryNode root;

    public void insert(int key) {

        BinaryNode node = new BinaryNode(key);  //������ڵ�

        if (root == null) {
            root = node;
        } else {
            BinaryNode current = root;
            BinaryNode parent;

            while (true) {

                parent = current;
                if (node.data > current.data) {
                    current = current.rightChild;
                    if (current == null) {
                        parent.rightChild = node;
                        return;
                    }
                } else {
                    current = current.leftChild;
                    if (current == null) {
                        parent.leftChild = node;
                        return;
                    }

                }
            }
        }
    }


    //����key
    public BinaryNode find(int key) {
        BinaryNode current = root;

        while (current.data != key) {
            if (key > current.data) {
                current = current.rightChild;
            } else {
                current = current.leftChild;
            }

            if (current == null) return null;
        }

        return current;
    }


    public void showNode(BinaryNode node) {
        if (node != null) {
            System.out.println(node.data);
        } else {
            System.out.println("null");
        }
    }


//    public BinaryNode delete(int key) {
//
//    }



    /** ����ͷ�����ʵ��Ⱥ�˳�� **/
    /**
     * �������
     * ������
     */
    public void preOrderTraverse(BinaryNode root) {
        if (root == null)
            return;

        System.out.print(root.data +" ");
        preOrderTraverse(root.leftChild);
        preOrderTraverse(root.rightChild);
    }

    /**
     * �������
     * ������
     */
    public void inOrderTraverse(BinaryNode root) {
        if (root == null)
            return;

        inOrderTraverse(root.leftChild);
        System.out.print(root.data +" ");
        inOrderTraverse(root.rightChild);
    }


    /**
     * ��������
     * ������
     */
    public void postOrderTraverse(BinaryNode root) {
        if (root == null)
            return;
        postOrderTraverse(root.leftChild);
        postOrderTraverse(root.rightChild);
        System.out.print(root.data +" ");
    }

}
