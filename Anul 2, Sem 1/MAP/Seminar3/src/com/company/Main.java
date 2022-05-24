package com.company;

import container.MyMap;
import domain.Student;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Student s1 = new Student("Dan", 4.5f);
        Student s2 = new Student("Ana", 8.5f);
        Student s3 = new Student("Dan", 4.5f);

        HashSet<Student> hashSet = new HashSet<Student>();

        hashSet.add(s1);
        hashSet.add(s2);
        hashSet.add(s3);

        hashSet.addAll(Arrays.asList(s1,s2,s3));
        for(Student s: hashSet)
            System.out.println(s);

        Comparator<Student> comparator = new Comparator<Student>(){
            @Override
            public int compare(Student o1, Student o2) {
                return s1.getNume().compareTo(s2.getNume());
            }
        };

        TreeSet<Student> treeset = new TreeSet<Student>(comparator);
        treeset.addAll(Arrays.asList(s1,s2,s3));
        System.out.println(treeset.toString());

        TreeMap<String, Student> studentTreeMap = new TreeMap<String, Student>();
        studentTreeMap.put(s1.getNume(), s1);
        studentTreeMap.put(s2.getNume(), s2);
        studentTreeMap.put(s3.getNume(), s3);

        //System.out.println(studentTreeMap.toString());

        for(Map.Entry<String, Student> pereche : studentTreeMap.entrySet())
            System.out.println(pereche.getKey() + " " + pereche.getValue().getMedie());

        MyMap myMap = new MyMap();
        myMap.add(s1);
        myMap.add(s2);
        myMap.add(s3);

        myMap.printAll();
    }
}
