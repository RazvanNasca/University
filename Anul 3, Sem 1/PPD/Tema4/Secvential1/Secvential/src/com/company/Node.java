package com.company;

class Node {
    private int coeff, exp;
    private Node next;

    public Node(int coeff, int exp) {
        this.coeff = coeff;
        this.exp = exp;
        this.next = null;
    }

    public int getCoeff() { return coeff; }

    public void setCoeff(int coeff) { this.coeff = coeff; }

    public int getExp() { return exp; }

    public void setExp(int exp) { this.exp = exp; }

    public Node getNext() { return next; }

    public void setNext(Node next) { this.next = next; }

    @Override
    public String toString() {
        return this.coeff + "x^" + this.exp;
    }
}
