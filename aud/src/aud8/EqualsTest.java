package aud8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class EqualsTest {

    public static <T> boolean equals(List<T> left, List<T> right){
        if (left.size() != right.size())
            return false;
//        for (int i = 0; i < left.size(); i++){
//            if (!left.get(i).equals(right.get(i))){
//                return false;
//            }
//        }
        return IntStream.range(0, left.size())
                .allMatch(i -> left.get(i).equals(right.get(i)));
//        return true;
    }

    public static void main(String[] args) {
        List<String> l1 = new ArrayList<>(List.of("A", "B", "C", "F"));
        List<String> l2 = new ArrayList<>(List.of("A", "B", "C", "D"));
        System.out.println(equals(l1, l2));
    }
}
