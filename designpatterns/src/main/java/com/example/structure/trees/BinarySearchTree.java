package com.example.structure.trees;

/**
    二叉搜索树，是指一棵空树或者具有下列性质的二叉树：

        若任意节点的左子树不空，则左子树上所有节点的值均小于它的根节点的值；
        若任意节点的右子树不空，则右子树上所有节点的值均大于它的根节点的值；
        任意节点的左，右子树也分别为二叉搜索树；
        没有键值相等的节点。
 **/
public class BinarySearchTree {

    private BinaryNode root;

    public void insert(int key) {

        BinaryNode node = new BinaryNode(key);  //待插入节点

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


    //查找key
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


    public BinaryNode delete(int key) {

    }



    /** 根据头结点访问的先后顺序 **/
    /**
     * 先序遍历
     * 中左右
     */
    public void preOrderTraverse(BinaryNode root) {
        if (root == null)
            return;

        System.out.print(root.data +" ");
        preOrderTraverse(root.leftChild);
        preOrderTraverse(root.rightChild);
    }

    /**
     * 中序遍历
     * 左中右
     */
    public void inOrderTraverse(BinaryNode root) {
        if (root == null)
            return;

        inOrderTraverse(root.leftChild);
        System.out.print(root.data +" ");
        inOrderTraverse(root.rightChild);
    }


    /**
     * 后续遍历
     * 左右中
     */
    public void postOrderTraverse(BinaryNode root) {
        if (root == null)
            return;
        postOrderTraverse(root.leftChild);
        postOrderTraverse(root.rightChild);
        System.out.print(root.data +" ");
    }

}
