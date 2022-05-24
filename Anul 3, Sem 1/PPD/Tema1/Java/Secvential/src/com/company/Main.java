package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    static int [][] matrix;
    static int [][] kernel;
    static int [][] result;
    static int N = 0, M = 0, n = 0, m = 0;
    static int offset;
    static int p = 16;

    public static void main(String[] args) throws InterruptedException {

        read();

        p = Integer.parseInt(args[0]);

        if(p == 1)
            sequential();
        else
            parallel();

        write();

    }

    public static void sequential()
    {
        long startTime = System.nanoTime();

        for(int i = 0; i < N; i++)
            for(int j = 0; j < M; j++)
                result[i][j] = computeConv(i, j);

        long endTime = System.nanoTime();
        System.out.println((double)(endTime - startTime) / 1E6);
    }

    public static void parallel() throws InterruptedException {
        Thread[] t = new Worker[p];

        int start = 0;
        int end = 0;
        int chunk = M / p;
        int rest = M % p;

        long startTime = System.nanoTime();

        for(int i = 0; i < t.length; i++) {
            end = start + chunk;

            if (rest > 0) {
                end++;
                rest--;
            }

            t[i] = new Worker(start, end);
            t[i].start();

            start = end;
        }

        for (Thread thread : t)
            thread.join();

        long endTime = System.nanoTime();
        System.out.println((double)(endTime - startTime) / 1E6);
    }

    public static void read()
    {
        try {
            File myObj = new File("D:\\Faculta\\Anul3,Sem1\\PPD\\Tema1\\Java\\Secvential\\src\\com\\company\\Date\\date.txt");
            Scanner myReader = new Scanner(myObj);

            if(myReader.hasNextLine()){
                String data = myReader.nextLine();
                String[] datas = data.split(" ");
                N = Integer.parseInt(datas[0]);
                M = Integer.parseInt(datas[1]);
                n = Integer.parseInt(datas[2]);
                m = Integer.parseInt(datas[3]);
            }

            matrix = new int [N][M];
            result = new int [N][M];

            for(int i = 0; i < N; i++)
                if (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] datas = data.split(" ");
                    for(int j = 0; j < M; j++)
                        matrix[i][j] = Integer.parseInt(datas[j]);
                }

            kernel = new int [n + 1][m + 1];

            for(int i = 0; i < n; i++)
                if (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] datas = data.split(" ");
                    for(int j = 0; j < m; j++)
                        kernel[i][j] = Integer.parseInt(datas[j]);
                }

            offset = n / 2;

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void write() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\Faculta\\Anul3,Sem1\\PPD\\Tema1\\Java\\Secvential\\src\\com\\company\\Date\\output.txt"));
            for (int[] elem : result) {
                for (int i : elem) {
                    bw.write(i + " ");
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int computeConv(int i, int j)
    {
        int result = 0;

        for(int ii = 0; ii < n; ii++)
        {
            int iv = i + ii - offset;
            for(int jj = 0; jj < m; jj++)
            {
                int jv = j + jj - offset;

                if(iv < 0)
                    iv = 0;
                if(iv >= N)
                    iv = N - 1;
                if(jv < 0)
                    jv = 0;
                if(jv >= M)
                    jv = M - 1;

                result += matrix[iv][jv] * kernel[ii][jj];
            }
        }

        return result;
    }

    public static class Worker extends Thread {

        private int start, end;

        public Worker(int start, int end){
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {

            if (N < M) {
                for (int i = 0; i < N; i++)
                    for (int j = start; j < end; j++)
                        result[i][j] = computeConv(i, j);
            }
            else
            {
                for (int i = start; i < end; i++)
                    for (int j = 0; j < M; j++)
                        result[i][j] = computeConv(i, j);
            }
        }

    }

}


