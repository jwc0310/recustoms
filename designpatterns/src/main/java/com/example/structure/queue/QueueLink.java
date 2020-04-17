package com.example.structure.queue;

public class QueueLink {

    private static class Node {
        private Object data;
        private Node next;

        public Node(Object data, Node next) {
            this.data = data;
            this.next = next;
        }
    }


    //��ͷ    ͷָ��
    private Node front;
    //��β    βָ��
    private Node rear;
    //����    ����
    private int size;

    public QueueLink() {
        front = null;
        rear = null;
        size = 0;
    }

    public boolean enqueue(Object o) {

        Node node = new Node(o, null);
        if (size == 0) {
            front = rear = node;
        } else {
            rear.next = node;
            rear = node;
        }
        size ++;
        return true;

    }


    public Object dequeue() {
        if (size == 0) {
            throw  new NullPointerException("queue is null");
        }

        Object data = rear.data;
        front = front.next;
        size --;

        return data;
    }


    public int size() {
        return size;
    }

}
