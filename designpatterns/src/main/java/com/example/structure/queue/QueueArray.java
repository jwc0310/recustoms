package com.example.structure.queue;


/**
 *
 * ���������� �����ȳ�
 *
 */

public class QueueArray<T> {

    private T[] datas;  //ʹ��������Ϊ����Ԫ������
    private int maxSize;  //��������
    private int front;    //ͷָ��
    private int rear;     //βָ��

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getFront() {
        return front;
    }

    public void setFront(int front) {
        this.front = front;
    }

    public int getRear() {
        return rear;
    }

    public void setRear(int rear) {
        this.rear = rear;
    }


    //��ʼ��ָ��
    public void initQueue() {
        this.front = 0;
        this.rear = 0;
    }

    //���г�ʼ��
    @SuppressWarnings("unchecked")
    public QueueArray(int maxSize) {
        if (maxSize < 1)
            maxSize = 1;

        this.maxSize = maxSize;
        this.front = 0;
        this.rear = 0;
        this.datas = (T[]) new Object[this.maxSize];
    }


    //���е�״̬: ����, �ӿ�
    public boolean isNull() {
        return this.front == this.rear;
    }

    public boolean isFull() {
        return (rear + 1) % this.maxSize == front;
    }

    //���в����� ���ӣ�����
    public boolean push(T data) {
        if (isFull()) {
            return false;  //���� �޷�����
        }

        datas[rear] = data;
        rear = (rear +  1) % maxSize;  //��βָ��+1
        return true;
    }

    public T pop() {
        if (isNull())
            return null;

        T popData = datas[front];
        front = (front + 1) % maxSize;
        return popData;
    }

    public T[] getDatas() {
        return datas;
    }


    public void setDatas(T[] datas) {
        this.datas = datas;
    }


    public static void main(String[] args) {
        QueueArray<Character> myqueue = new QueueArray<>(5);

        //pan kong
        System.out.println("is null ? " + myqueue.isNull());
        System.out.println("is full ? " + myqueue.isFull());

        myqueue.push('A');
        myqueue.push('B');
        myqueue.push('C');
        myqueue.push('D');
        myqueue.push('E');

        System.out.println("is null ? " + myqueue.isNull());
        System.out.println("is full ? " + myqueue.isFull());

        Character data1 = myqueue.pop();

        System.out.println("pop " + data1);

        System.out.println("is null ? " + myqueue.isNull());
        System.out.println("is full ? " + myqueue.isFull());

    }


}
