package com.company;

import java.util.Arrays;
import java.util.List;

public class Oricum {

    public static void main(String[] args)
    {
        List<Integer> list = Arrays.asList(1,2,-3,4,3,6);
        /*list.stream()
                .sorted((a,b) -> b.compareTo(a))
                .forEach(x -> System.out.println(x + " "));
        */

        list.stream().sorted(Integer::compareTo);
        System.out.println(list);
    }

}
