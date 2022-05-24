package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class MyLinkedList {

    private Node head;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void insert(Node node) {
        Node iterator = this.head;
        if(this.head == null) {
            this.head = node;
            return;
        }
        while (iterator.getNext() != null && iterator.getExp() > node.getExp())
            iterator = iterator.getNext();

        if (this.head.getExp() < node.getExp()) {
            node.setNext(this.head);
            this.head = node;
            this.size++;
            return;
        }
        node.setNext(iterator.getNext());
        iterator.setNext(node);
        this.size++;
    }

    public void add(Node node) {
        if (this.get(node.getExp()) == null) {
            this.insert(node);
            return;
        }
        Node iterator = this.head;
        if (this.head == null)
            return;

        while (iterator.getNext() != null && iterator.getExp() != node.getExp())
            iterator = iterator.getNext();

        iterator.setCoeff(iterator.getCoeff() + node.getCoeff());
    }

    public Node get(int exponent) {
        if (this.head == null)
            return null;

        Node iterator = this.head;
        while (iterator != null && iterator.getExp() != exponent)
            iterator = iterator.getNext();

        return iterator;
    }

    public void writeToFile() {
        Node iterator = this.head;
        StringBuilder result = new StringBuilder();
        while(iterator.getNext() != null) {
            if (iterator.getCoeff() != 0) {
                result.append(iterator.getCoeff()).append("x^").append(iterator.getExp()).append(" + ");
            }
            iterator = iterator.getNext();
        }
        result.append(iterator.getCoeff()).append("x^").append(iterator.getExp());

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Faculta\\Anul3,Sem1\\PPD\\Tema4\\Secvential\\src\\data\\output.txt"));
            writer.write(String.valueOf(result));
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
