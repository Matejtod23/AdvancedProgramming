package aud4.ArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListTest {
    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>(100);
        integerList.add(3);
        integerList.add(4);
        integerList.add(5);
        integerList.add(6);
        integerList.add(7);
        integerList.add(11);

        List<String> stringList = new ArrayList<>();
        stringList.add("A");
        stringList.add("BCD");
        stringList.add("KJSAH");

        System.out.println(integerList);
        System.out.println(stringList);

        System.out.println(integerList.get(3));
        System.out.println(stringList.get(2));

        System.out.println(integerList.contains(3));
        System.out.println(integerList.contains(5));

        integerList.removeIf(i -> i < 5);
        System.out.println(integerList);

        System.out.println(stringList.isEmpty());

        System.out.println(stringList.indexOf("BCD"));

        stringList = stringList.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList()); // za da gi stavime vo lista potoa so = mesto '.' se dodavaat na strinList
                //.forEach(System.out::println);//samo da gi isprinta ne gi zapamtuva vo listata
        System.out.println(stringList);
    }
}
