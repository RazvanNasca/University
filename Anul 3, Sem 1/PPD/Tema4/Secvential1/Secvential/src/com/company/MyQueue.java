package com.company;

import java.util.ArrayList;
import java.util.List;

class MyQueue {

    private List<Node> elements;
    private Node front;
    private int size;

    public MyQueue() {
        this.elements = new ArrayList<>();
        this.front = null;
        this.size = 0;
    }

    public Node peek() {
        return this.front;
    }

    public void enqueue(Node element) {
        if (isEmpty()) {
            this.front = element;
        }
        this.elements.add(element);
        this.size++;
    }

    public void dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is already empty!");
            return;
        }
        if (this.size == 1)
            this.front = null;
        else
            this.front = this.elements.get(1);

        this.elements.remove(0);
        this.size--;
    }

    public boolean isEmpty() {
        return (this.size == 0);
    }
}
