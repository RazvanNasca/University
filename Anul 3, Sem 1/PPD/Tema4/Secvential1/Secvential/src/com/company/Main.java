package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

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
                    queue.enqueue(node);
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("An error occurred while reading: ");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        long startTime = System.nanoTime();
        int noFiles = 5; /// 5, 10

        MyLinkedList list = new MyLinkedList();
        MyQueue queue = new MyQueue();
        readPolynomials(queue, noFiles);

        while (!queue.isEmpty()) {
            Node node = queue.peek();
            list.add(node);
            queue.dequeue();
        }

        list.writeToFile();

        long endTime = System.nanoTime();

        System.out.println((double)(endTime - startTime) / 1E6);
    }
}
