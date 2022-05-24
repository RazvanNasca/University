package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.StrictMath.sqrt;

public class Main {

    /*
        Afiseaza ultimul cuvant dpdv alfabetic dintr-o propozitie
        Input: sir - String -> sirul initial
        Output: un string -> cuvantul cautat
    */

    static private String ultimul(String sir)
    {
        String[] cuvant = sir.split(" ");
        String maxim = cuvant[0];

        for(int i = 1; i < cuvant.length; i++)
            if(maxim.compareTo(cuvant[i]) < 0)
                maxim = cuvant[i];

       return maxim;
    }

    static private void testUltimul()
    {
        assert ultimul("Ana are mere").equals("mere");
        assert ultimul("Ana are mere si pere").equals("si");
        assert ultimul("Ana are pere").equals("pere");
    }

    /*
        Calculeaza distanta euclidiana dintre 2 puncte
        Input:  x1, y1 - Double -> coordonatele primului punct
                x2, y2 - Double -> coordonatele punctului doi
        Output: un Double reprezentand distanta euclidiana dintre cele 2 puncte
    */
    static private Double distanta(Double x1, Double y1, Double x2, Double y2)
    {
        Double disX = x1 - x2;
        Double disY = y1 - y2;
        return sqrt(disX * disX + disY * disY);
    }

    static private void testDistanta()
    {
        assert distanta(1.0,5.0,4.0,1.0).equals(5.0);
        assert distanta(2.0,2.0,4.0,2.0).equals(2.0);
    }

    /*
        Calculeaza produsul scalar intre 2 vectori rari
        Input: v1 - Lista de intregi, v2 - Lista de intregi
        Output: un intreg reprezentand produsul scalar intre cei 2 vectori rari
     */
    static private Integer scalar(List<Integer> v1, List<Integer> v2)
    {
        int prod = 0;
        for(int i = 0; i < v1.size(); i++)
            prod += v1.get(i) * v2.get(i);

        return prod;
    }

    static private void testScalar()
    {
        List<Integer> v1 = new ArrayList<>();
        List<Integer> v2 = new ArrayList<>();

        v1.add(1); v1.add(0); v1.add(2); v1.add(0); v1.add(3);
        v2.add(1); v2.add(2); v2.add(0); v2.add(3); v2.add(1);

        assert scalar(v1, v2) == 4;
    }

    /*
        Transforma un numar din baza 10 in baza 2
        Input: x - Integer -> numarul in baza 10
        Output: un numar intreg in baza 2
     */
    static private Integer Zece2Doi(Integer x)
    {
        int rez = 0;
        int putere = 1;

        while(x != 0)
        {
            int rest = x % 2;
            rez += rest * putere;
            x = x / 2;
            putere *= 10;
        }

        return rez;
    }

    /*
        Afiseaza toate numerele din intervalul [1, n] scrise in baza 2
        Input: n - Integer -> capatul intervalului
        Output: o lista de intregi care contine numerele in baza 2 cerute
     */
    static private List<Integer> binar(Integer n)
    {
        List<Integer> rez = new ArrayList<>();
        for(int i = 1; i <= n; i++)
            rez.add(Zece2Doi(i));

        return rez;
    }

    static private void testBinar()
    {
        System.out.println(binar(4));
        System.out.println(binar(7));
    }

    /*
        Returneaza al k-lea element din lista
        Input: lista -> lista de intregi, k -> intreg
        Output: elementul de pe pozitia k
     */
    static private Integer kElement(List<Integer> lista, Integer k)
    {
        List<Integer> rez = lista.stream().sorted().collect(Collectors.toList());
        System.out.println(rez);
        return rez.get(lista.size() - k);
    }

    static private void testkElement()
    {
        List<Integer> lista = new ArrayList<>();
        lista.add(7); lista.add(4); lista.add(6); lista.add(3); lista.add(9); lista.add(1);

        assert kElement(lista, 2) == 7;
    }

    /*
        Gaseste elementul care se repeta
        Input: lista -> lista de intregi
        Output: un intreg reprezentand elementul care se repeta
     */
    static private Integer intrus(List<Integer> lista)
    {
        int suma = 0;
        int n = lista.size();
        for(int i = 0; i < n; i++)
            suma += lista.get(i);

        int gauss = n * (n - 1) / 2;

        return suma - gauss;
    }

    static private void testIntrus()
    {
        List<Integer> lista = new ArrayList<>();
        lista.add(1); lista.add(2); lista.add(3); lista.add(4); lista.add(2);

        assert intrus(lista) == 2;
        assert intrus2(lista) == 2;
    }

    static private Integer maxim1(int[][] matrice, int n, int m)
    {
        int maxim = -1;
        int lmax = 0;
        for(int i = 0; i < n; i++)
        {
            int suma = 0;
            for(int j = 0; j < m; j++)
                suma += matrice[i][j];

            if(maxim < suma)
            {
                maxim = suma;
                lmax = i;
            }
        }

        return lmax;
    }

    /// TEMA
    static private Integer maxim2(int[][] matrice, int n, int m)
    {
        int minim = 999;
        int lmin = 0;
        for(int i = 0; i < n; i++)
        {
            int gasit = 0;
            for(int j = 0; j < m; j++)
                if(matrice[i][j] == 1 && gasit == 0)
                {
                    gasit = 1;
                    if(minim > j)
                    {
                        minim = j;
                        lmin = i;
                    }
                }
        }

        return lmin;
    }

    static private void testMaxim1()
    {
        int[][] matrice = new int [100][100];

        matrice[0][0] = 0; matrice[0][1] = 0; matrice[0][2] = 0; matrice[0][3] = 1; matrice[0][4] = 1;
        matrice[1][0] = 0; matrice[1][1] = 1; matrice[1][2] = 1; matrice[1][3] = 1; matrice[1][4] = 1;
        matrice[2][0] = 0; matrice[2][1] = 0; matrice[2][2] = 1; matrice[2][3] = 1; matrice[2][4] = 1;

        assert maxim1(matrice, 3,5)  == 1;
        assert maxim2(matrice, 3,5)  == 1;
    }

    /// TEMA
    static private Integer intrus2(List<Integer> lista)
    {
        int [] frecv = new int[101];
        for(int i = 0; i < 100; i++)
            frecv[i] = 0;

        int rez = 0;
        int n = lista.size();
        for(int i = 0; i < n; i++)
            if(frecv[lista.get(i)] == 1)
                rez = lista.get(i);
            else
                frecv[lista.get(i)]++;

        return rez;
    }

    ///TEMA
   static private Integer majoritar(int[] sir, int n)
   {
       int[] frecv = new int[101];
       for(int i = 0; i < 100; i++)
           frecv[i] = 0;

       for(int i = 0; i < n; i++)
           frecv[sir[i]]++;

       int rez = 0;
       for(int i = 0; i < 100; i++)
           if(frecv[i] > n/2)
               rez = i;

       return rez;
   }

   static private void testMajoritar()
   {
       int[] sir = new int[100];
       int n = 11;
       sir[0] = 2; sir[1] = 8; sir[2] = 7; sir[3] = 2; sir[4] = 2; sir[5] = 5;
       sir[6] = 2; sir[7] = 3; sir[8] = 1; sir[9] = 2; sir[10] = 2;

       assert majoritar(sir, n) == 2;
   }

    public static void main(String[] args)
    {
        testUltimul();
        testDistanta();
        testScalar();
        testBinar();
        testkElement();
        testIntrus();
        testMaxim1();
        testMajoritar();
    }
}
