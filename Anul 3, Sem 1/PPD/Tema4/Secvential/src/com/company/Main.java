package com.company;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private static Lock lockList = new ReentrantLock();
    private static Lock lockQueue = new ReentrantLock();
    private static boolean done = false;

    private static void readPolynomials(MyQueue queue, int numberOfFiles) {
        File myObj;
        Scanner myReader;
        String templateFilename = "D:\\Faculta\\Anul3,Sem1\\PPD\\Tema4\\Secvential\\src\\data\\P?.txt";
        try{
            for (int i = 1; i <= numberOfFiles; i++) {
                String tmpFilename = templateFilename.replace('?', String.valueOf(i).charAt(0));
                myObj = new File(tmpFilename);
                myReader = new Scanner(myObj);

                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    int coeff = Integer.parseInt(data.split(" ")[0]);
                    int exp = Integer.parseInt(data.split(" ")[1]);
                    Node node = new Node(coeff, exp);

                    lockQueue.lock();
                    queue.enqueue(node);
                    lockQueue.unlock();
                }
            }
            done = true;
        }catch (FileNotFoundException e){
            System.out.println("An error occurred while reading: ");
            e.printStackTrace();
        }
    }

    public static void calculate(MyQueue queue, MyLinkedList list) {
        while (!queue.isEmpty() || !done) {
            lockQueue.lock();
            Node node = queue.peek();
            lockQueue.unlock();

            if(node != null) {
                lockList.lock();
                list.add(node);
                lockList.unlock();

                lockQueue.lock();
                queue.dequeue();
                lockQueue.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        long startTime = System.nanoTime();
        int noFiles = 5; /// 5, 10
        int noThreads = 8; /// 4, 6, 8

        Thread [] th = new Thread[noThreads];
        MyLinkedList list = new MyLinkedList();
        MyQueue queue = new MyQueue();

        th[0] = new Thread(() -> readPolynomials(queue, noFiles));

        for(int i = 1; i < noThreads; i++)
            th[i] = new Thread(() -> calculate(queue, list));

        for(int i = 0; i < noThreads; i++)
            th[i].start();

        for(int i = 0; i < noThreads; i++)
            th[i].join();


        list.writeToFile();

        long endTime = System.nanoTime();

        System.out.println((double)(endTime - startTime) / 1E6);
    }
}
