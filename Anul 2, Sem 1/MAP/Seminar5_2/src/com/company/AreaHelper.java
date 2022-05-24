package com.company;

import java.util.List;

public class AreaHelper {

    public static <E> void printArie(List<E> l, Area<E> f)
    {
        l.forEach(x -> System.out.println(f.getArea(x)));
    }
}
