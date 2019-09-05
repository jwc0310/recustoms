package com.example.structure.queue;


/**
 *
 * 队列特征： 先入先出
 *
 */

public class QueueArray<T> {

    private T[] datas;  //使用数组作为队列元素容器
    private int maxSize;  //队列容量
    private int front;    //头指针
    private int rear;     //尾指针

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


    //初始化指针
    public void initQueue() {
        this.front = 0;
        this.rear = 0;
    }

    //队列初始化
    @SuppressWarnings("unchecked")
    public QueueArray(int maxSize) {
        if (maxSize < 1)
            maxSize = 1;

        this.maxSize = maxSize;
        this.front = 0;
        this.rear = 0;
        this.datas = (T[]) new Object[this.maxSize];
    }


    //队列的状态: 队满, 队空
    public boolean isNull() {
        return this.front == this.rear;
    }

    public boolean isFull() {
        return (rear + 1) % this.maxSize == front;
    }

    //队列操作： 进队，出队
    public boolean push(T data) {
        if (isFull()) {
            return false;  //队满 无法进入
        }

        datas[rear] = data;
        rear = (rear +  1) % maxSize;  //队尾指针+1
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
